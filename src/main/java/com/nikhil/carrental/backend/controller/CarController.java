package com.nikhil.carrental.backend.controller;

import com.nikhil.carrental.backend.entity.Car;
import com.nikhil.carrental.backend.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService CarService;

    // ✅ CREATE CAR
    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return CarService.createCar(car);
    }

    // ✅ GET ALL CARS
    @GetMapping
    public List<Car> getAllCars() {
        return CarService.getAllCars();
    }
    @GetMapping("/{registrationNumber}")
    public Car findByRegistrationNumber(@PathVariable Long id) {
        return (Car) CarService.getCarById(id);
    }

    // ✅ GET CAR BY ID
    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id) {
        return (Car) CarService.getCarById(id);
    }

    // ✅ UPDATE CAR
    @PutMapping("/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody Car updatedCar) {
        return (Car) CarService.updateCar();
    }

    // ✅ DELETE CAR
    @DeleteMapping("/{id}")
    public String deleteCar(@PathVariable Long id) {
        CarService.deleteCar(id);
        return "Car deleted successfully!";
    }
}