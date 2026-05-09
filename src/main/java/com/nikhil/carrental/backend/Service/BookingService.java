//package com.nikhil.carrental.backend.Service;
//
//import com.nikhil.carrental.backend.entity.*;
//import com.nikhil.carrental.backend.repository.*;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class BookingService {
//
//    private final BookingRepository bookingRepository;
//    private final UserRepository userRepository;
//    private final CarRepository carRepository;
//
//    // ✅ Constructor Injection
//    public BookingService(BookingRepository bookingRepository,
//                          UserRepository userRepository,
//                          CarRepository carRepository) {
//        this.bookingRepository = bookingRepository;
//        this.userRepository = userRepository;
//        this.carRepository = carRepository;
//    }
//
//    // ✅ CREATE BOOKING (JWT-based user)
//    public Booking createBooking(Long carId, String start, String end) {
//
//        // 🔐 Get logged-in user from JWT
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String email = auth.getName();
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Car car = carRepository.findById(carId)
//                .orElseThrow(() -> new RuntimeException("Car not found"));
//
//        if (!car.isAvailable()) {
//            throw new RuntimeException("Car is not available");
//        }
//
//        Booking booking = new Booking();
//        booking.setUser(user);
//        booking.setCar(car);
//        booking.setPickupDateTime(start);
//        booking.setReturnDateTime(end);
//
//        // ✅ FIXED ENUMS
//        booking.setStatus(BookingStatus.BOOKED);
//
//        // 🚗 Mark car unavailable
//        car.setAvailable(false);
//        carRepository.save(car);
//
//        return bookingRepository.save(booking);
//    }
//
//    // ✅ GET ALL BOOKINGS (ADMIN use)
//    public List<Booking> getAllBookings() {
//        return bookingRepository.findAll();
//    }
//
//    // ✅ GET USER BOOKINGS
//    public List<Booking> getMyBookings() {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String email = auth.getName();
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        return bookingRepository.findByUser(user);
//    }
//
//    // ✅ COMPLETE BOOKING
//    public Booking completeBooking(Long bookingId) {
//
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new RuntimeException("Booking not found"));
//
//        booking.setStatus(BookingStatus.COMPLETED);
//
//        // 🚗 Make car available again
//        Car car = booking.getCar();
//        car.setAvailable(true);
//        carRepository.save(car);
//
//        return bookingRepository.save(booking);
//    }
//
//    // ✅ CANCEL BOOKING
//    public Booking cancelBooking(Long bookingId) {
//
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new RuntimeException("Booking not found"));
//
//        booking.setStatus(BookingStatus.CANCELLED);
//
//        // 🚗 Make car available again
//        Car car = booking.getCar();
//        car.setAvailable(true);
//        carRepository.save(car);
//
//        return bookingRepository.save(booking);
//    }
//
//    // ✅ DELETE BOOKING
//    public void deleteBooking(Long bookingId) {
//
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new RuntimeException("Booking not found"));
//
//        bookingRepository.delete(booking);
//    }
//}
//package com.nikhil.carrental.backend.Service;
//
//import com.nikhil.carrental.backend.constant.Enums;
//import com.nikhil.carrental.backend.entity.Booking;
//import com.nikhil.carrental.backend.entity.Car;
//import com.nikhil.carrental.backend.entity.User;
//import com.nikhil.carrental.backend.repository.BookingRepository;
//import com.nikhil.carrental.backend.repository.CarRepository;
//import com.nikhil.carrental.backend.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//public class BookingService {
//
//    @Autowired
//    private BookingRepository bookingRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private CarRepository carRepository;
//
//    public Booking createBooking(
//            Long userId,
//            Long carId,
//            LocalDateTime pickupDateTime,
//            LocalDateTime returnDateTime) {
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Car car = carRepository.findById(carId)
//                .orElseThrow(() -> new RuntimeException("Car not found"));
//
//        Booking booking = new Booking();
//        booking.setUser(user);
//        booking.setCar(car);
//
//        booking.setPickupDateTime(pickupDateTime);
//        booking.setReturnDateTime(returnDateTime);
//
//        booking.setStatus(Enums.BOOKED);
//
//        return bookingRepository.save(booking);
//    }
//

//    public List<Booking> getAllBookings() {
//        return bookingRepository.findAll();
//    }
//
//    public Booking getBookingById(Long id) {
//        return bookingRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Booking not found"));
//    }
//
//    public void deleteBooking(Long id) {
//        bookingRepository.deleteById(id);
//    }
//}
//

     package com.nikhil.carrental.backend.Service;
     import com.nikhil.carrental.backend.constant.BookingStatus;
     import com.nikhil.carrental.backend.constant.Role;
     import com.nikhil.carrental.backend.entity.Booking;
     import com.nikhil.carrental.backend.entity.Car;
     import com.nikhil.carrental.backend.entity.User;
     import com.nikhil.carrental.backend.repository.BookingRepository;
     import com.nikhil.carrental.backend.repository.CarRepository;
     import com.nikhil.carrental.backend.repository.UserRepository;
     import org.springframework.beans.factory.annotation.Autowired;
     import org.springframework.security.core.context.SecurityContextHolder;
     import org.springframework.stereotype.Service;

     import java.time.LocalDateTime;
     import java.util.List;

@Service

public class BookingService{
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    // CREATE BOOKING (USER)
    public Booking createBooking(Long carId,
                                 LocalDateTime pickup,
                                 LocalDateTime drop) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        if (!car.isAvailable()) {
            throw new RuntimeException("Car not available");
        }

        if (pickup == null || drop == null) {
            throw new RuntimeException("Pickup or return date missing");
        }

        if (pickup.isAfter(drop)) {
            throw new RuntimeException("Invalid booking dates");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setCar(car);
        booking.setPickupDateTime(pickup);
        booking.setReturnDateTime(drop);
        booking.setStatus(BookingStatus.BOOKED);

        car.setAvailable(false);
        carRepository.save(car);

        return bookingRepository.save(booking);
    }

    // USER → own bookings
    public List<Booking> getMyBookings() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bookingRepository.findByUser(user);
    }

    // ADMIN → all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // USER + ADMIN → get booking by id
    public Booking getBookingById(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // USER can only access own booking
        if (user.getRole() == Role.CUSTOMER &&
                !booking.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        return booking;
    }

    // USER → cancel booking
    public Booking cancelBooking(Long id) {

        Booking booking = getBookingById(id);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!booking.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        booking.setStatus(BookingStatus.CANCELLED);

        Car car = booking.getCar();
        car.setAvailable(true);
        carRepository.save(car);

        return bookingRepository.save(booking);
    }

    // ADMIN → complete booking
    public Booking completeBooking(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(BookingStatus.COMPLETED);

        Car car = booking.getCar();
        car.setAvailable(true);
        carRepository.save(car);

        return bookingRepository.save(booking);
    }

    // ADMIN → delete booking
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}