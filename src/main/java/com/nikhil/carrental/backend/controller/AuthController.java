//package com.nikhil.carrental.backend.controller;
//
//import com.nikhil.carrental.backend.dto.LoginRequest;
//import com.nikhil.carrental.backend.entity.User;
//import com.nikhil.carrental.backend.repository.UserRepository;
//import com.nikhil.carrental.backend.security.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequest request) {
//
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!user.getPassword().equals(request.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
//
//        return jwtUtil.generateToken(user); // ✅ FIXED
//    }

//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequest request) {
//
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!user.getPassword().equals(request.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
//
//        return jwtUtil.generateToken(User.getEmail());
//    }
//}

//package com.nikhil.carrental.backend.controller;
//
//import com.nikhil.carrental.backend.constant.Role;
//import com.nikhil.carrental.backend.dto.LoginRequest;
//import com.nikhil.carrental.backend.dto.RegisterRequest;
//import com.nikhil.carrental.backend.entity.User;
//import com.nikhil.carrental.backend.repository.UserRepository;
//import com.nikhil.carrental.backend.security.JwtUtil;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    // ✅ LOGIN API
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequest request) {
//
//        String email = request.getEmail().trim(); // 🔥 avoid hidden spaces
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!user.getPassword().equals(request.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
//
//        return jwtUtil.generateToken(user); // ✅ correct
//    }
//
//    // ✅ REGISTER API
//    @PostMapping("/register")
//    public String register(@RequestBody RegisterRequest request) {
//
//        String email = request.getEmail().trim();
//
//        if (userRepository.findByEmail(email).isPresent()) {
//            throw new RuntimeException("User already exists");
//        }
//
//        User user = new User();
//        user.setName(request.getName());
//        user.setEmail(email);
//        user.setPassword(request.getPassword());
//        Role role;
//
//        try {
//            role = Role.valueOf(request.getRole().toUpperCase());
//        } catch (Exception e) {
//            throw new RuntimeException("Invalid role. Use CUSTOMER or ADMIN");
//        }
//
//        user.setRole(role);
//        userRepository.save(user);
//
//        return "User registered successfully";
//    }
//}
package com.nikhil.carrental.backend.controller;

import com.nikhil.carrental.backend.constant.Role;
import com.nikhil.carrental.backend.dto.LoginRequest;
import com.nikhil.carrental.backend.dto.RegisterRequest;
import com.nikhil.carrental.backend.entity.User;
import com.nikhil.carrental.backend.repository.UserRepository;
import com.nikhil.carrental.backend.security.JwtUtil;

import org.antlr.v4.runtime.atn.ActionTransition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173") // FIXED CORS
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    //  LOGIN API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));


        if (Boolean.parseBoolean(user.getClass(request.getPassword(), user.getPassword()))) {
            String token = jwtUtil.generateToken(user.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", user.getRole());
            response.put("email", user.getEmail());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

    }
    // ✅ REGISTER API
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {

        String email = request.getEmail().trim();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(email);
        user.setPassword(request.getPassword());

        Role role;
        try {
            role = Role.valueOf(request.getRole().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Invalid role. Use CUSTOMER or ADMIN");
        }

        user.setRole(role);
        userRepository.save(user);

        return "User registered successfully";
    }
}