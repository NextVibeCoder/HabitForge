package com.example.habitforgeapi.strategy;

import com.example.habitforgeapi.model.Habito;
import java.time.LocalDate;

public interface FrecuenciaStrategy {

    /**
     * @param habito 
     * @param fecha  
     * @return true 
     */
    boolean isMandatoryDay(Habito habito, LocalDate fecha);
}
