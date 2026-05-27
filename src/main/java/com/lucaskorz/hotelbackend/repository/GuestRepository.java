package com.lucaskorz.hotelbackend.repository;

import com.lucaskorz.hotelbackend.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    boolean existsByDocument(String document);

    boolean existsByDocumentAndIdNot(String document, Long id);

    List<Guest> findByNameContainingIgnoreCaseOrDocumentContainingOrPhoneContaining(
            String name,
            String document,
            String phone
    );
}
