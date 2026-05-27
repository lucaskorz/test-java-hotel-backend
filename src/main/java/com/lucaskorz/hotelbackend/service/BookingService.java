package com.lucaskorz.hotelbackend.service;

import com.lucaskorz.hotelbackend.dto.CreateBookingRequest;
import com.lucaskorz.hotelbackend.dto.GuestBookingSummaryResponse;
import com.lucaskorz.hotelbackend.dto.UpdateBookingRequest;
import com.lucaskorz.hotelbackend.entity.Booking;
import com.lucaskorz.hotelbackend.entity.Guest;
import com.lucaskorz.hotelbackend.repository.BookingRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final GuestService guestService;
    private final BookingRepository bookingRepository;

    public BookingService(GuestService guestService, BookingRepository bookingRepository) {
        this.guestService = guestService;
        this.bookingRepository = bookingRepository;
    }

    private Double mountsDailyValue(LocalDate date, boolean hasVehicle) {
        double totalValue = 0.0;

        boolean weekend = date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;

        totalValue += weekend ? 150 : 120;
        if (hasVehicle) {
            totalValue += weekend ? 20 : 15;
        }

        return totalValue;
    }

    private Double calculateBookingValue(CreateBookingRequest request) {
        LocalDate current = request.getCheckInDate().toLocalDate();
        LocalDate checkout = request.getCheckOutDate().toLocalDate();

        Double totalValue = 0.0;

        while (current.isBefore(checkout)) {
            totalValue += this.mountsDailyValue(current, request.getHasVehicle());
            current = current.plusDays(1);
        }

        if (!request.getCheckOutDate().toLocalTime().isBefore(LocalTime.of(16, 30))) {
            totalValue += this.mountsDailyValue(checkout, request.getHasVehicle());
        }

        return totalValue;
    }

    private void validateBookingDates(LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("As datas são obrigatórias");
        }

        if (checkInDate.equals(checkOutDate)) {
            throw new IllegalArgumentException("Check-in e check-out não podem ser iguais");
        }

        if (checkOutDate.isBefore(checkInDate)) {
            throw new IllegalArgumentException("Check-out não pode ser menor que check-in");
        }

        if (checkInDate.toLocalDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in não pode ser inferior à data de hoje");
        }
    }

    public Booking create(CreateBookingRequest request) {
        this.validateBookingDates(request.getCheckInDate(), request.getCheckOutDate());

        Guest guest = this.guestService.findGuestByDocumentOrPhoneOrName(request.getGuest());
        Double totalValue = this.calculateBookingValue(request);

        Booking booking = new Booking();

        booking.setGuest(guest);
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setHasVehicle(request.getHasVehicle());
        booking.setValue(totalValue);

        return bookingRepository.save(booking);
    }

    public List<Booking> list(String sort) {
        if (sort == null) {
            return bookingRepository.findAll();
        }

        boolean validSort = sort.equals("checkInDate")
                || sort.equals("checkOutDate")
                || sort.equals("hasVehicle");

        if (!validSort) {
            throw new RuntimeException("Ordenação inválida");
        }

        return bookingRepository.findAll(Sort.by(sort));
    }

    public Booking findById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking não encontrado"));
    }

    public Booking update(Long id, UpdateBookingRequest request) {
        this.validateBookingDates(request.getCheckInDate(), request.getCheckOutDate());

        Guest guest = this.guestService.findGuestByDocumentOrPhoneOrName(request.getGuest());

        Booking checkIn = this.findById(id);

        checkIn.setGuest(guest);
        checkIn.setCheckInDate(request.getCheckInDate());
        checkIn.setCheckOutDate(request.getCheckOutDate());
        checkIn.setHasVehicle(request.getHasVehicle());

        return bookingRepository.save(checkIn);
    }

    public void delete(Long id) {
        this.findById(id);
        bookingRepository.deleteById(id);
    }

    private List<GuestBookingSummaryResponse> buildSummary(
            List<Booking> bookings
    ) {
        Map<Long, List<Booking>> grouped = bookings.stream()
                .collect(Collectors.groupingBy(
                        booking -> booking.getGuest().getId()
                ));

        List<GuestBookingSummaryResponse> response = new ArrayList<>();

        for (List<Booking> guestBookings : grouped.values()) {
            Guest guest = guestBookings.get(0).getGuest();

            Double totalSpent = guestBookings.stream()
                    .mapToDouble(Booking::getValue)
                    .sum();

            Booking lastBooking = guestBookings.stream()
                    .max(Comparator.comparing(Booking::getCheckOutDate))
                    .orElseThrow();

            GuestBookingSummaryResponse item =
                    new GuestBookingSummaryResponse();

            item.setName(guest.getName());
            item.setDocument(guest.getDocument());
            item.setPhone(guest.getPhone());
            item.setTotalSpent(totalSpent);
            item.setLastBookingValue(lastBooking.getValue());

            response.add(item);
        }

        return response;
    }

    public List<GuestBookingSummaryResponse> findCheckedOutGuests() {
        List<Booking> bookings = bookingRepository
                .findByCheckOutDateBefore(LocalDateTime.now());

        return buildSummary(bookings);
    }

    public List<GuestBookingSummaryResponse> findActiveGuests() {
        List<Booking> bookings = bookingRepository
                .findByCheckOutDateAfter(LocalDateTime.now());

        return buildSummary(bookings);
    }
}
