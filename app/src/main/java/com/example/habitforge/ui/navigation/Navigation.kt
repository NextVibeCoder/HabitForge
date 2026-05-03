package com.example.habitforge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habitforge.ui.screen.SignUpScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    // Cambiamos temporalmente el startDestination a SignUp para probar la pantalla creada
    NavHost(navController = navController, startDestination = SignUp) {

        composable<SplashScreen> {
            // Pantalla de carga (vacía por ahora)
        }
        
        composable<SignIn> {
            // Aquí iría SignInScreen cuando esté lista
        }
        
        composable<SignUp> {
            SignUpScreen(
                onNavigateToSignIn = {
                    navController.navigate(SignIn)
                },
                onSignUpSuccess = {
                    navController.navigate(Home)
                }
            )
        }
        
        composable<Home> {
            // Pantalla Home (vacía por ahora)
        }

        composable<CreateHabit> { }
        composable<Squad> {  }
        // Comentamos HabitDetails porque usa la clase Habit que podría dar problemas de serialización
        // composable<HabitDetails> { }
        composable<Profile> {}
        composable<Levels>{}
    }
}
