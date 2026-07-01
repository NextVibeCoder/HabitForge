package com.example.habitforgeapi.config;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TimeProvider {

    LocalDate getCurrentDate();

    LocalDateTime getCurrentDateTime();
}
