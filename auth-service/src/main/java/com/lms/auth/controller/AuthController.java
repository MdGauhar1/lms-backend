package com.lms.auth.controller;

import com.lms.auth.model.User;
import com.lms.auth.security.JwtUtil;
import com.lms.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {

        service.register(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        return ResponseEntity.ok(response);
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {

        User dbUser = service.findByUsername(user.getUsername());

        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(dbUser);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("role", dbUser.getRole());
        response.put("userId", dbUser.getId());

        return ResponseEntity.ok(response);
    }
}











//package com.lms.auth.controller;
//
//import com.lms.auth.model.User;
//import com.lms.auth.security.JwtUtil;
//import com.lms.auth.service.AuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private AuthService service;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    // ✅ REGISTER
//    @PostMapping("/register")
//    public String register(@RequestBody User user) {
//        service.register(user);
//        return "User Registered";
//    }
//
//    // ✅ LOGIN
//    @PostMapping("/login")
//    public String login(@RequestBody User user) {
//
//        User dbUser = service.findByUsername(user.getUsername());
//
//        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
//            throw new RuntimeException("Invalid credentials");
//        }
//
//        // ✅ CORRECT TOKEN GENERATION
//        return jwtUtil.generateToken(dbUser);
//    }
//}