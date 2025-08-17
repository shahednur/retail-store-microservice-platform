package com.shahed.authservice.auth_service.service;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shahed.authservice.auth_service.dto.CreateUserRequest;
import com.shahed.authservice.auth_service.dto.UserResponse;
import com.shahed.authservice.auth_service.entity.Role;
import com.shahed.authservice.auth_service.entity.User;
import com.shahed.authservice.auth_service.exception.EmailAlreadyExistsException;
import com.shahed.authservice.auth_service.exception.UserNotFoundException;
import com.shahed.authservice.auth_service.exception.UsernameAlreadyExistsException;
import com.shahed.authservice.auth_service.repository.RefreshTokenRepository;
import com.shahed.authservice.auth_service.repository.RoleRepository;
import com.shahed.authservice.auth_service.repository.UserRepository;
import com.shahed.authservice.auth_service.util.ApiResponse;
import com.shahed.authservice.auth_service.util.JwtUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private JwtUtil JwtUtil;

    // Register a new user
    public ApiResponse<Map<String, String>> register(String username, String rawPassword, String roleName) {
        if (userRepository.existsByUsername(username)) {
            return ApiResponse.error("USERNAME_TAKEN");
        }

        Role role = roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(Role.builder().name(roleName).build()));

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .enabled(true)
                .roles(Set.of(role))
                .build();

        user = userRepository.save(user);
        return ApiResponse.ok(Map.of("username", user.getUsername()));
    }
}
