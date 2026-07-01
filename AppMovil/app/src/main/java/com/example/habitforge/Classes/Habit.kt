package com.example.habitforge.Classes

import kotlinx.serialization.Serializable

/* Clase temporal, con la implementacion real y supabase lo vamos a cambiar */

@Serializable
enum class HabitFrequency {
    DAILY,
    WEEKLY
}

@Serializable
data class Habit(val id: Int,
                 val name: String = "",
                 val description: String? = null,
                 val emoji: String,
                 val frequency: HabitFrequency = HabitFrequency.DAILY,
) {





}