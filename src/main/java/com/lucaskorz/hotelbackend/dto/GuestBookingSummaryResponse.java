package com.lucaskorz.hotelbackend.dto;

public class GuestBookingSummaryResponse {

    private String name;
    private String document;
    private String phone;
    private Double totalSpent;
    private Double lastBookingValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Double getLastBookingValue() {
        return lastBookingValue;
    }

    public void setLastBookingValue(Double lastBookingValue) {
        this.lastBookingValue = lastBookingValue;
    }
}