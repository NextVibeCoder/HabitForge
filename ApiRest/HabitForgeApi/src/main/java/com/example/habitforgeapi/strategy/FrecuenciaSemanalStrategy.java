package com.example.habitforgeapi.strategy;

import com.example.habitforgeapi.model.DiaSemana;
import com.example.habitforgeapi.model.Habito;
import com.example.habitforgeapi.model.HabitoDiaSemana;
import com.example.habitforgeapi.repository.HabitoDiaSemanaRepository;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Component("SEMANAL")
public class FrecuenciaSemanalStrategy implements FrecuenciaStrategy {

    private final HabitoDiaSemanaRepository habitoDiaSemanaRepository;


    private static final Map<DayOfWeek, DiaSemana> DAY_MAP = Map.of(
            DayOfWeek.MONDAY, DiaSemana.LUNES,
            DayOfWeek.TUESDAY, DiaSemana.MARTES,
            DayOfWeek.WEDNESDAY, DiaSemana.MIERCOLES,
            DayOfWeek.THURSDAY, DiaSemana.JUEVES,
            DayOfWeek.FRIDAY, DiaSemana.VIERNES,
            DayOfWeek.SATURDAY, DiaSemana.SABADO,
            DayOfWeek.SUNDAY, DiaSemana.DOMINGO
    );

    public FrecuenciaSemanalStrategy(HabitoDiaSemanaRepository habitoDiaSemanaRepository) {
        this.habitoDiaSemanaRepository = habitoDiaSemanaRepository;
    }

    @Override
    public boolean isMandatoryDay(Habito habito, LocalDate fecha) {
        DiaSemana targetDay = DAY_MAP.get(fecha.getDayOfWeek());
        List<HabitoDiaSemana> diasConfigurados = habitoDiaSemanaRepository.findByHabitoId(habito.getId());
        return diasConfigurados.stream()
                .anyMatch(hds -> hds.getDiaSemana() == targetDay);
    }
}
