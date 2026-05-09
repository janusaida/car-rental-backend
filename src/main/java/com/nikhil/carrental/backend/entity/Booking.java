package com.nikhil.carrental.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nikhil.carrental.backend.constant.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data   // 🔥 No getters/setters needed
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔥 PICKUP DATE + TIME
    @Column(nullable = false)
    private LocalDateTime pickupDateTime;

    // 🔥 RETURN DATE + TIME
    @Column(nullable = false)
    private LocalDateTime returnDateTime;

    // 👤 USER RELATION
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    // 🚗 CAR RELATION
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Car car;


    private BookingStatus status;
}