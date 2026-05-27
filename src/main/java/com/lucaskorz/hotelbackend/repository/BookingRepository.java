package com.lucaskorz.hotelbackend.repository;

import com.lucaskorz.hotelbackend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCheckOutDateBefore(LocalDateTime date);

    List<Booking> findByCheckOutDateAfter(LocalDateTime date);
}
