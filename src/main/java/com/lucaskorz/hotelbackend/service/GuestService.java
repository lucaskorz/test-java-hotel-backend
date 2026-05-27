package com.lucaskorz.hotelbackend.service;

import com.lucaskorz.hotelbackend.dto.CreateGuestRequest;
import com.lucaskorz.hotelbackend.dto.GuestCreateBookingRequest;
import com.lucaskorz.hotelbackend.entity.Guest;
import com.lucaskorz.hotelbackend.repository.GuestRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {
    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public Guest create(CreateGuestRequest request) {
        boolean existsByDocument = guestRepository.existsByDocument(request.getDocument());

        if (existsByDocument) {
            throw new RuntimeException("Hóspede já cadastrado");
        }

        Guest guest = new Guest();

        guest.setName(request.getName());
        guest.setDocument(request.getDocument());
        guest.setPhone(request.getPhone());

        return guestRepository.save(guest);
    }

    public List<Guest> list(String sort) {
        if (sort == null) {
            return guestRepository.findAll();
        }

        boolean validSort = sort.equals("name")
                || sort.equals("document")
                || sort.equals("phone");

        if (!validSort) {
            throw new RuntimeException("Ordenação inválida");
        }

        return guestRepository.findAll(Sort.by(sort));
    }

    public Guest findById(Long id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hóspede não encontrado"));
    }

    public Guest update(Long id, CreateGuestRequest request) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hóspede não encontrado"));

        boolean existsByDocument = guestRepository.existsByDocumentAndIdNot(
                request.getDocument(),
                id
        );

        if (existsByDocument) {
            throw new RuntimeException("Documento já cadastrado");
        }

        guest.setName(request.getName());
        guest.setDocument(request.getDocument());
        guest.setPhone(request.getPhone());

        return guestRepository.save(guest);
    }

    public void delete(Long id) {
        boolean existsById = guestRepository.existsById(id);

        if (!existsById) {
            throw new RuntimeException("Hóspede não existe!");
        }

        guestRepository.deleteById(id);
    }

    public Guest findGuestByDocumentOrPhoneOrName(GuestCreateBookingRequest guestCreateBookingRequest) {
        List<Guest> listGuests = this.guestRepository.findByNameContainingIgnoreCaseOrDocumentContainingOrPhoneContaining(
                guestCreateBookingRequest.getName(), guestCreateBookingRequest.getDocument(), guestCreateBookingRequest.getPhone()
        );

        if (listGuests == null || listGuests.size() <= 0) {
            throw new RuntimeException("Hóspede não encontrado");
        }

        return listGuests.get(0);
    }
}
