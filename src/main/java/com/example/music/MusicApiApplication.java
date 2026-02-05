package com.example.music;

import com.example.music.model.User;
import com.example.music.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootApplication
@EnableScheduling
public class MusicApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner initAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminUsername = "admin";
            String adminPassword = "password";

            Optional<User> existingAdmin = userRepository.findByUsername(adminUsername);

            if (existingAdmin.isEmpty()) {
                User adminUser = new User();
                adminUser.setUsername(adminUsername);
                adminUser.setPassword(passwordEncoder.encode(adminPassword));
                adminUser.setRole("ADMIN");
                userRepository.save(adminUser);
                System.out.println(">>> Admin user created successfully!");
            } else {
                User adminUser = existingAdmin.get();
                // Optionally, reset password if it doesn't match, for recovery purposes
                if (!passwordEncoder.matches(adminPassword, adminUser.getPassword())) {
                    adminUser.setPassword(passwordEncoder.encode(adminPassword));
                    userRepository.save(adminUser);
                    System.out.println(">>> Admin user password has been reset.");
                }
            }
        };
    }
}
