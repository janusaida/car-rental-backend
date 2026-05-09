//package com.nikhil.carrental.backend.controller;
//
//import com.nikhil.carrental.backend.dto.BookingRequest;
//import com.nikhil.carrental.backend.entity.Booking;
//import com.nikhil.carrental.backend.Service.BookingService; // ⚠️ lowercase 'service'
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/bookings")
//public class BookingController {
//
//    @Autowired
//    private BookingService bookingService;
//
//    @PreAuthorize("hasRole('USER')")
//    @PostMapping
//    public Booking createBooking(@RequestBody BookingRequest request) {
//        return bookingService.createBooking(
//                request.getUserId(),
//                request.getCarId(),
//                request.getPickupDateTime(),
//                request.getReturnDateTime()
//        );
//    }
//
//    @PostMapping
//    public Booking createBooking(@RequestBody BookingRequest request) {
//        return bookingService.createBooking(
//                request.getUserId(),
//                request.getCarId(),
//                request.getPickupDateTime(),
//                request.getReturnDateTime()
//        );
//    }
//
//    @GetMapping
//    public List<Booking> getAllBookings() {
//        return bookingService.getAllBookings(); // ✅ FIXED
//    }
//
//    @GetMapping("/{id}")
//    public Booking getBookingById(@PathVariable Long id) {
//        return bookingService.getBookingById(id);
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteBooking(@PathVariable Long id) {
//        bookingService.deleteBooking(id);
//        return "Booking deleted successfully";
//    }
//}

package com.nikhil.carrental.backend.controller;

import com.nikhil.carrental.backend.dto.BookingRequest;
import com.nikhil.carrental.backend.entity.Booking;
import com.nikhil.carrental.backend.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // ✅ USER → Create booking
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public Booking createBooking(@RequestBody BookingRequest request) {
        return bookingService.createBooking(
                request.getCarId(),
                request.getPickupDateTime(),
                request.getReturnDateTime()
        );
    }

    // ✅ USER → View own bookings
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my")
    public List<Booking> getMyBookings() {
        return bookingService.getMyBookings();
    }

    // ✅ ADMIN → View all bookings
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // ✅ USER + ADMIN → Get booking by ID
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    // ✅ USER → Cancel booking
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/cancel/{id}")
    public Booking cancelBooking(@PathVariable Long id) {
        return bookingService.cancelBooking(id);
    }

    // ✅ ADMIN → Complete booking
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/complete/{id}")
    public Booking completeBooking(@PathVariable Long id) {
        return bookingService.completeBooking(id);
    }

    // ✅ ADMIN → Delete booking
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "Booking deleted successfully";
    }
}