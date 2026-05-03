package com.example.habitforge.ui.navigation

import com.example.habitforge.Classes.Habit
import kotlinx.serialization.Serializable

@Serializable
object SplashScreen

@Serializable
object SignIn

@Serializable
object SignUp

@Serializable
object Home

@Serializable
object CreateHabit

@Serializable
data class HabitDetails(
    val habit: Habit,
)

@Serializable
object Squad

@Serializable
object Profile

@Serializable
object Levels
