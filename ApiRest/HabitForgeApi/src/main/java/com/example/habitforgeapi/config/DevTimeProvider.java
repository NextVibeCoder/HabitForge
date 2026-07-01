package com.example.habitforgeapi.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Profile({"dev", "test"})
public class DevTimeProvider implements TimeProvider {

    private final AtomicInteger daysOffset = new AtomicInteger(0);

    @Override
    public LocalDate getCurrentDate() {
        return LocalDate.now().plusDays(daysOffset.get());
    }

    @Override
    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now().plusDays(daysOffset.get());
    }

    public void advanceDays(int days) {
        daysOffset.addAndGet(days);
    }

    public void rewindDays(int days) {
        daysOffset.addAndGet(-days);
    }

    public void resetOffset() {
        daysOffset.set(0);
    }

    public int getOffset() {
        return daysOffset.get();
    }
}
