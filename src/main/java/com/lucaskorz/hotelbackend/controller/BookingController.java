package com.lucaskorz.hotelbackend.controller;

import com.lucaskorz.hotelbackend.dto.CreateBookingRequest;
import com.lucaskorz.hotelbackend.dto.GuestBookingSummaryResponse;
import com.lucaskorz.hotelbackend.dto.UpdateBookingRequest;
import com.lucaskorz.hotelbackend.entity.Booking;
import com.lucaskorz.hotelbackend.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking createBooking(@Valid @RequestBody CreateBookingRequest request) {
        return bookingService.create(request);
    }

    @GetMapping("/checked-out")
    public List<GuestBookingSummaryResponse> checkedOut() {
        return bookingService.findCheckedOutGuests();
    }

    @GetMapping("/active")
    public List<GuestBookingSummaryResponse> active() {
        return bookingService.findActiveGuests();
    }

    @GetMapping
    public List<Booking> listBookings(@RequestParam(required = false) String sort) {
        return bookingService.list(sort);
    }

    @GetMapping("/{id}")
    public Booking findBookingById(@PathVariable Long id) {
        return bookingService.findById(id);
    }

    @PutMapping("/{id}")
    public Booking updateBooking(
            @PathVariable Long id,
            @Valid @RequestBody UpdateBookingRequest request
    ) {
        return bookingService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(@PathVariable Long id) {
        bookingService.delete(id);
    }
}
