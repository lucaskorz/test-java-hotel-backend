package com.lucaskorz.hotelbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "guests",
        indexes = {
                @Index(name = "idx_guest_document", columnList = "document")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_guest_document",
                        columnNames = "document"
                )
        }
)
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 11)
    private String document;

    @Column(nullable = false, length = 11)
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}