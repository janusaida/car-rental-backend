package com.nikhil.carrental.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;

    private String model;

    private double pricePerDay;

    private boolean available;

    // 🔥 UNIQUE REGISTRATION NUMBER
    @Column(unique = true, nullable = false)
    private String registrationNumber;

    // ✅ Constructors
    public Car() {}

    public Car(Long id, String brand, String model, double pricePerDay, boolean available, String registrationNumber) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.available = available;
        this.registrationNumber = registrationNumber;
    }

    // ✅ Getters & Setters
//    public Long getId() {
//        return id;
//    }
//
//    public String getBrand() {
//        return brand;
//    }
//
//    public String getModel() {
//        return model;
//    }
//
//    public double getPricePerDay() {
//        return pricePerDay;
//    }
//
//    public boolean isAvailable() {
//        return available;
//    }
//
//    public String getRegistrationNumber() {
//        return registrationNumber;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setBrand(String brand) {
//        this.brand = brand;
//    }
//
//    public void setModel(String model) {
//        this.model = model;
//    }
//
//    public void setPricePerDay(double pricePerDay) {
//        this.pricePerDay = pricePerDay;
//    }
//
//    public void setAvailable(boolean available) {
//        this.available = available;
//    }
//
//    public void setRegistrationNumber(String registrationNumber) {
//        this.registrationNumber = registrationNumber;
//    }
}