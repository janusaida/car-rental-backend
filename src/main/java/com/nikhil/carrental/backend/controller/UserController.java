package com.nikhil.carrental.backend.controller;

import com.nikhil.carrental.backend.entity.User;
import com.nikhil.carrental.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // ✅ CREATE USER
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // ✅ GET ALL USERS
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ GET USER BY ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // ✅ UPDATE USER
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.getId();
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());

        // 🔒 Only update password if provided
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(updatedUser.getPassword());
        }

        return userRepository.save(user);
    }

    // ✅ DELETE USER
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        userRepository.delete(user);

        return "User deleted successfully!";
    }
}