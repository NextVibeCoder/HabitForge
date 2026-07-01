package com.example.habitforgeapi.strategy;

import com.example.habitforgeapi.model.Habito;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component("DIARIA")
public class FrecuenciaDiariaStrategy implements FrecuenciaStrategy {

    @Override
    public boolean isMandatoryDay(Habito habito, LocalDate fecha) {
        return true;
    }
}
