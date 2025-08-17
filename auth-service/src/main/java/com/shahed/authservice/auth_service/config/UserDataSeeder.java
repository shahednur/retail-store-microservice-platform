package com.shahed.authservice.auth_service.config;

import com.shahed.authservice.auth_service.entity.Role;
import com.shahed.authservice.auth_service.entity.User;
import com.shahed.authservice.auth_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Random random = new Random();

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("User data already exists. Skipping seeding.");
            return;
        }

        log.info("Seeding 10 test users...");

        IntStream.rangeClosed(1, 10).forEach(i -> {
            String username = "user" + i;
            String email = "user" + i + "@demo.com";
            String rawPassword = "password" + i;

            User user = User.builder()
                    .username(username)
                    // .email(email)
                    .password(passwordEncoder.encode(rawPassword))
                    // .role(Role.ADMIN)
                    .build();

            userRepository.save(user);
            log.info("Created user: {}", username);
        });

        log.info("Seeding completed.");
    }
}
