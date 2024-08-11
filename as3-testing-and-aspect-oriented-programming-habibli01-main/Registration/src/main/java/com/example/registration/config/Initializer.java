package com.example.registration.config;

import com.example.registration.entity.Registration;
import com.example.registration.repository.RegistrationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Initializer {

    @Bean
    public CommandLineRunner populateDatabase(RegistrationRepository registrationRepository) {
        return args -> {
            // Create some sample registrations
            Registration reg1 = new Registration("John", "Doe", "password123", "USER");
            Registration reg2 = new Registration("Jane", "Doe", "password456", "ADMIN");
            Registration reg3 = new Registration("Alice", "Smith", "password789", "USER");

            // Save to the database
            registrationRepository.save(reg1);
            registrationRepository.save(reg2);
            registrationRepository.save(reg3);

            // Log to confirm
            registrationRepository.findAll().forEach(registration ->
                    System.out.println("Saved registration: " + registration));
        };
    }
}
