package com.example.habitforgeapi.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Profile("prod")
public class ProductionTimeProvider implements TimeProvider {

    @Override
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    @Override
    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}
