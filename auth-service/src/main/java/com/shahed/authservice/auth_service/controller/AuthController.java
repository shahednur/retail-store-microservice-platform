package com.shahed.authservice.auth_service.controller;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.shahed.authservice.auth_service.dto.CreateUserRequest;
import com.shahed.authservice.auth_service.dto.UserResponse;
import com.shahed.authservice.auth_service.service.AuthService;
import com.shahed.authservice.auth_service.util.ApiResponse;

import jakarta.validation.Valid;
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

    // @GetMapping("/login")
    // public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestParam
    // String username, @RequestParam String password){
    // return ResponseEntity.ok();
    // }

}
