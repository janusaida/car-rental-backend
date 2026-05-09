package com.nikhil.carrental.backend.repository;

import com.nikhil.carrental.backend.entity.Booking;
import com.nikhil.carrental.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // 🔥 1. OVERLAP VALIDATION (MOST IMPORTANT)
    @Query("SELECT b FROM Booking b WHERE b.car.id = :carId " +
            "AND b.pickupDateTime < :returnTime " +
            "AND b.returnDateTime > :pickupTime")
    List<Booking> findOverlappingBookings(Long carId,
                                          LocalDateTime pickupTime,
                                          LocalDateTime returnTime);


    // 🔍 2. GET BOOKINGS BY USER
    List<Booking> findByUserId(Long userId);
    List<Booking> findByUser(User user);


    // 🔍 3. GET BOOKINGS BY CAR
    List<Booking> findByCarId(Long carId);


    // 🔍 4. GET BOOKINGS IN DATE RANGE (optional)
    @Query("SELECT b FROM Booking b WHERE b.pickupDateTime >= :start " +
            "AND b.returnDateTime <= :end")
    List<Booking> findBookingsBetween(LocalDateTime start, LocalDateTime end);
}

