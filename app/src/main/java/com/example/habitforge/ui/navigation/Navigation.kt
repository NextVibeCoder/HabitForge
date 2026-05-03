package com.example.habitforge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habitforge.ui.screen.AddHabitScreen
import com.example.habitforge.ui.screen.ProfileScreen
import com.example.habitforge.ui.screen.SignUpScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    // Configuramos Profile como destino inicial para probar la nueva pantalla
    NavHost(navController = navController, startDestination = Profile) {

        composable<SplashScreen> {
            // Pantalla de carga
        }
        
        composable<SignIn> {
            // Pantalla de Inicio de Sesión
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
        
        composable<CreateHabit> {
            AddHabitScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onInitializeMission = {
                    navController.navigate(Home)
                }
            )
        }
        
        composable<Profile> {
            ProfileScreen(
                onNavigateToSettings = {
                    // Acción para ir a ajustes
                },
                onMissionClick = {
                    navController.navigate(CreateHabit)
                },
                onLogClick = {
                    // Acción para ir al log
                },
                onSquadClick = {
                    navController.navigate(Squad)
                }
            )
        }
        
        composable<Home> {
            // Pantalla Home
        }

        composable<Squad> {  }
        composable<Levels>{}
    }
}
