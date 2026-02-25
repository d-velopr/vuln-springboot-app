package com.dvelupmint.app.config;

import com.dvelupmint.app.model.User;
import com.dvelupmint.app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    private static final Logger log = LoggerFactory.getLogger(AdminInitializer.class);

    private static final String ADMIN_EMAIL = "superadmin@email.com";
    private static final String ADMIN_USERNAME = "superadmin";
    private static final String ADMIN_PASSWORD = "admin123";

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail(ADMIN_EMAIL).isEmpty()) {
                log.warn("Creating default admin user...");
                User admin = new User();
                admin.setEmail(ADMIN_EMAIL);
                admin.setUsername(ADMIN_USERNAME);
                admin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
                admin.setRole("ADMIN");
                userRepository.save(admin);
                log.warn("ADMIN CREATED → login with: {} / {}", ADMIN_EMAIL, ADMIN_PASSWORD);
            } else {
                log.warn("Admin already exists");
            }
        };
    }
}