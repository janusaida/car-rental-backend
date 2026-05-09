//package com.nikhil.carrental.backend.Service;
//
//import com.nikhil.carrental.backend.entity.Car;
//import com.nikhil.carrental.backend.repository.CarRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class CarService {
//
//    @Autowired
//    private CarRepository carRepository;
//
//    // ✅ CREATE CAR
//    public Car createCar(Car car) {
//
//        // 🔴 Validation
//        if (car.getRegistrationNumber() == null || car.getRegistrationNumber().isEmpty()) {
//            throw new RuntimeException("Registration number is required");
//        }
//
//        // 🔴 Duplicate check
//        if (carRepository.existsByRegistrationNumber(car.getRegistrationNumber())) {
//            throw new RuntimeException("Car with this registration number already exists!");
//        }
//
//        System.out.println("🔥 Creating car: " + car.getRegistrationNumber());
//
//        return carRepository.save(car);
//    }
//
//    // ✅ GET ALL CARS
//    public List<Car> getAllCars() {
//        return carRepository.findAll();
//    }
//
//    // ✅ GET CAR BY ID
//    public Car getCarById(Long id) {
//        return carRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
//    }
//
//    // ✅ UPDATE CAR
//    public Car updateCar(Long id, Car updatedCar) {
//
//        Car existingCar = carRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Car not found"));
//
//        // 🔴 Handle registration number change
//        if (updatedCar.getRegistrationNumber() != null &&
//                !updatedCar.getRegistrationNumber().equals(existingCar.getRegistrationNumber())) {
//
//            if (carRepository.existsByRegistrationNumber(updatedCar.getRegistrationNumber())) {
//                throw new RuntimeException("Registration number already exists!");
//            }
//
//            existingCar.setRegistrationNumber(updatedCar.getRegistrationNumber());
//        }
//
//        // ✅ Update fields
//        existingCar.setBrand(updatedCar.getBrand());
//        existingCar.setModel(updatedCar.getModel());
//        existingCar.setPricePerDay(updatedCar.getPricePerDay());
//        existingCar.setAvailable(updatedCar.isAvailable());
//
//        return carRepository.save(existingCar);
//    }
//
//    // ✅ DELETE CAR
//    public void deleteCar(Long id) {
//
//        Car car = carRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Car not found"));
//
//        carRepository.delete(car);
//    }
//
//    // 🚗 RETURN CAR USING REGISTRATION NUMBER
//    public Car returnCar(String registrationNumber) {
//
//        Car car = carRepository.findByRegistrationNumber(registrationNumber)
//                .orElseThrow(() -> new RuntimeException("Car not found with registration number: " + registrationNumber));
//
//        if (car.isAvailable()) {
//            throw new RuntimeException("Car is already available (not currently booked)");
//        }
//
//        car.setAvailable(true);
//
//        return carRepository.save(car);
//    }
//}
package com.nikhil.carrental.backend.Service;

import com.nikhil.carrental.backend.entity.Car;
import com.nikhil.carrental.backend.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    // ✅ ADMIN → add car
    public Car createCar(Car car) {
        car.setAvailable(true);
        return carRepository.save(car);
    }

    // ✅ PUBLIC
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
    public List<Car> getCarById(Long id) {
        return carRepository.findAll();
    }
    public List<Car> updateCar() {
        return carRepository.findAll();
    }
    public List<Car> findByRegistrationNumber() {
        return carRepository.findAll();
    }
    // ✅ ADMIN
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}