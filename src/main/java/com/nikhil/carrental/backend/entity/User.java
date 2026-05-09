package com.nikhil.carrental.backend.entity;

import com.nikhil.carrental.backend.constant.Role;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public String getClass(String password, String password1) {
        return password;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthorities();
    }

//    public Long getId() {
//        return id;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//
//    public String getEmail() {   // ✅ FIX
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
}