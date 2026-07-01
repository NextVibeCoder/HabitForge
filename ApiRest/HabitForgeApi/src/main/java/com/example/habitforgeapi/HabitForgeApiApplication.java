package com.example.habitforgeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HabitForgeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HabitForgeApiApplication.class, args);
    }

}
