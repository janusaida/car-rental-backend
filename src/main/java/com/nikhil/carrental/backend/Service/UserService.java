//package com.nikhil.carrental.backend.Service;
//
//import com.nikhil.carrental.backend.constant.Role;
//import com.nikhil.carrental.backend.entity.User;
//import com.nikhil.carrental.backend.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    // ✅ CREATE USER (REGISTER)
//    public User createUser(User user) {
//
//        // 🔥 check email already exists
//        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
//            throw new RuntimeException("Email already registered!");
//        }
//
//        // 🔐 encrypt password
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        // ✅ ALWAYS set USER role (security)
//        user.setRole(Role.USER);
//
//        return userRepository.save(user);
//    }
//
//    // ✅ CREATE ADMIN (ONLY ADMIN CAN DO THIS)
//    public User createAdmin(User user) {
//
//        // 🔐 check current logged-in user role
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth == null || auth.getAuthorities().stream()
//                .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//            throw new RuntimeException("Only ADMIN can create another ADMIN");
//        }
//
//        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
//            throw new RuntimeException("Email already registered!");
//        }
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRole(Role.ADMIN);
//
//        return userRepository.save(user);
//    }
//
//    // ✅ LOGIN VALIDATION
//    public User login(String email, String password) {
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
//
//        return user;
//    }
//
//    // ✅ GET ALL USERS (ADMIN ONLY)
//    public List<User> getAllUsers() {
//
//        checkAdminAccess();
//
//        return userRepository.findAll();
//    }
//
//    // ✅ GET USER BY ID (USER can access own / ADMIN can access any)
//    public User getUserById(Long id) {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String email = auth.getName();
//
//        User currentUser = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        User targetUser = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // ✅ allow if ADMIN or same user
//        if (currentUser.getRole() != Role.ADMIN &&
//                !currentUser.getId().equals(targetUser.getId())) {
//            throw new RuntimeException("Access denied");
//        }
//
//        return targetUser;
//    }
//
//    // ✅ UPDATE USER (USER → own, ADMIN → anyone)
//    public User updateUser(Long id, User updatedUser) {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String email = auth.getName();
//
//        User currentUser = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        User user = getUserById(id);
//
//        // ❌ prevent role change by normal user
//        if (currentUser.getRole() != Role.ADMIN) {
//            updatedUser.setRole(user.getRole());
//        }
//
//        user.setName(updatedUser.getName());
//        user.setEmail(updatedUser.getEmail());
//
//        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
//            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
//        }
//
//        return userRepository.save(user);
//    }
//
//    // ✅ DELETE USER (ADMIN ONLY)
//    public void deleteUser(Long id) {
//
//        checkAdminAccess();
//
//        if (!userRepository.existsById(id)) {
//            throw new RuntimeException("User not found");
//        }
//
//        userRepository.deleteById(id);
//    }
//
//    // 🔐 COMMON ADMIN CHECK
//    private void checkAdminAccess() {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth == null || auth.getAuthorities().stream()
//                .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//            throw new RuntimeException("Access denied: ADMIN only");
//        }
//    }
//}

package com.nikhil.carrental.backend.Service;

import com.nikhil.carrental.backend.constant.Role;
import com.nikhil.carrental.backend.entity.User;
import com.nikhil.carrental.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ REGISTER
    public User createUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CUSTOMER);

        return userRepository.save(user);
    }

    // ✅ LOGIN
    public User login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    // ✅ ADMIN → all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ USER / ADMIN
    public User getUserById(Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User current = userRepository.findByEmail(email).orElseThrow();
        User target = userRepository.findById(id).orElseThrow();

        if (current.getRole() != Role.ADMIN &&
                !current.getId().equals(target.getId())) {
            throw new RuntimeException("Access denied");
        }

        return target;
    }
}