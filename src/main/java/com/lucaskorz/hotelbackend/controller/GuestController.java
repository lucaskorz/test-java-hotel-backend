package com.lucaskorz.hotelbackend.controller;

import com.lucaskorz.hotelbackend.dto.CreateGuestRequest;
import com.lucaskorz.hotelbackend.entity.Guest;
import com.lucaskorz.hotelbackend.service.GuestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guests")
public class GuestController {
    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping
    public Guest createGuest(@Valid @RequestBody CreateGuestRequest request) {
        return guestService.create(request);
    }

    @GetMapping
    public List<Guest> listGuests(@RequestParam(required = false) String sort) {
        return guestService.list(sort);
    }

    @GetMapping("/{id}")
    public Guest findGuestById(@PathVariable Long id) {
        return guestService.findById(id);
    }

    @PutMapping("/{id}")
    public Guest updateGuest(
            @PathVariable Long id,
            @Valid @RequestBody CreateGuestRequest request
    ) {
        return guestService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGuest(@PathVariable Long id) {
        guestService.delete(id);
    }
}
