package com.nikhil.carrental.backend.repository;

import com.nikhil.carrental.backend.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    // 🔍 Check duplicate registration number
    boolean existsByRegistrationNumber(String registrationNumber);

    // 🔍 Find by registration number (for return flow)
    Optional<Car> findByRegistrationNumber(String registrationNumber);
}