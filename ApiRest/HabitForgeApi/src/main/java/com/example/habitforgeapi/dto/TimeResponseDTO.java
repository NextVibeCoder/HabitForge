package com.example.habitforgeapi.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimeResponseDTO {

    private LocalDate currentDate;
    private LocalDateTime currentDateTime;
    private int daysOffset;

    public TimeResponseDTO() {
    }

    public TimeResponseDTO(LocalDate currentDate, LocalDateTime currentDateTime, int daysOffset) {
        this.currentDate = currentDate;
        this.currentDateTime = currentDateTime;
        this.daysOffset = daysOffset;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    public LocalDateTime getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(LocalDateTime currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public int getDaysOffset() {
        return daysOffset;
    }

    public void setDaysOffset(int daysOffset) {
        this.daysOffset = daysOffset;
    }
}
