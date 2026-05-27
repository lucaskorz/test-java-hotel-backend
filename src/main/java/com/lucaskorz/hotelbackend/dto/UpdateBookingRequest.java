package com.lucaskorz.hotelbackend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class UpdateBookingRequest {

    @Valid
    @NotNull(message = "Hóspede é obrigatório")
    private GuestCreateBookingRequest guest;

    @NotNull(message = "Informação de veículo é obrigatória")
    private Boolean hasVehicle;

    @NotNull(message = "Data de entrada é obrigatória")
    private LocalDateTime checkInDate;

    @NotNull(message = "Data de saída é obrigatória")
    private LocalDateTime checkOutDate;

    public LocalDateTime getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDateTime checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDateTime getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDateTime checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public GuestCreateBookingRequest getGuest() {
        return guest;
    }

    public void setGuest(GuestCreateBookingRequest guest) {
        this.guest = guest;
    }

    public Boolean getHasVehicle() {
        return hasVehicle;
    }

    public void setHasVehicle(Boolean hasVehicle) {
        this.hasVehicle = hasVehicle;
    }
}