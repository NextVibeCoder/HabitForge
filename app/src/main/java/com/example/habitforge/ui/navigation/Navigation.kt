package com.example.habitforge.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.habitforge.Classes.Habit
import com.example.habitforge.Classes.HabitFrequency

@Composable
fun Navigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SplashScreen){

        composable<SplashScreen> {
        }
        composable<SignIn> {  }
        composable<SignUp> { }
        composable<Home> {}

        composable<CreateHabit> { }
        composable<Squad> {  }
        composable<HabitDetails> { }
        composable<Profile> {}
        composable<Levels>{}
    }
}
