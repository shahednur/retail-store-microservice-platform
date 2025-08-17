package com.shahed.authservice.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import com.shahed.authservice.auth_service.service.AuthService;
import com.shahed.authservice.auth_service.util.ApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Creating a new user
    @GetMapping("/register")
    public ResponseEntity<ApiResponse<Map<String, String>>> register(@RequestParam String username,
            @RequestParam String password, @RequestParam(defaultValue = "USER") String role) {
        return ResponseEntity.ok(authService.register(username, password, role));
    }

    @GetMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestParam String username,
            @RequestParam String password) {
        return ResponseEntity.ok(authService.login(username, password));
    }

    @GetMapping("/refresh")
    public ResponseEntity<ApiResponse<Map<String, String>>> refresh(@RequestParam String refreshToken) {
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestParam String refreshToken,
            @RequestHeader(name = "Authorization", required = false) String authHeader) {
        String accessToken = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            accessToken = authHeader.substring(7);
        }
        authService.logout(refreshToken, accessToken);
        return ResponseEntity.ok(ApiResponse.ok("logged_out"));
    }

}
