package com.example.habitforge.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object SignIn

@Serializable
object SignUp

@Serializable
object Home

@Serializable
object Log

@Serializable
object Friends

@Serializable
object Profile

@Serializable
object CreateHabit

@Serializable
data class EditHabit(val id: Long)

@Serializable
data class HabitView(val id: Long)
