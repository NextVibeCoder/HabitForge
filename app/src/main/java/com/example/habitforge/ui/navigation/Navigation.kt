package com.example.habitforge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habitforge.ui.screen.*

@Composable
fun Navigation() {
    val navController = rememberNavController()

    // Iniciamos la aplicación en la pantalla de Inicio de Sesión (SignIn)
    NavHost(navController = navController, startDestination = SignIn) {

        // --- FLUJO DE AUTENTICACIÓN ---
        
        composable<SignIn> {
            SignIn(
                onNavigateToRegister = {
                    navController.navigate(SignUp)
                },
                onSignInSuccess = {
                    // Simulación de login exitoso
                    navController.navigate(Home) {
                        popUpTo(SignIn) { inclusive = true }
                    }
                },
                onNavigateToForgotPassword = {
                    // Implementar si existe la ruta
                }
            )
        }

        composable<SignUp> {
            SignUpScreen(
                onNavigateToSignIn = {
                    navController.navigate(SignIn)
                },
                onSignUpSuccess = {
                    // Simulación de registro exitoso
                    navController.navigate(Home) {
                        popUpTo(SignUp) { inclusive = true }
                    }
                }
            )
        }

        // --- FLUJO PRINCIPAL (HOME & SECCIONES) ---

        composable<Home> {
            Home(
                onNavigateToCreateHabit = {
                    navController.navigate(CreateHabit)
                },
                onNavigateToProfile = {
                    navController.navigate(Profile)
                },
                onNavigateToSquad = {
                    navController.navigate(Friends)
                },
                onNavigateToHabitDetail = {
                    navController.navigate(HabitView)
                },
                onNavigateToHome = {
                    // Ya estamos en Home
                }
            )
        }

        composable<Log> {
            LogScreen(
                onMissionClick = { navController.navigate(CreateHabit) },
                onLogClick = { /* Ya estamos aquí */ },
                onSquadClick = { navController.navigate(Friends) },
                onBaseClick = { navController.navigate(Profile) }
            )
        }

        composable<Friends> {
            FriendsScreen(
                onNavigateToHome = { navController.navigate(Home) },
                onNavigateToProfile = { navController.navigate(Profile) },
                onNavigateToSquad = { /* Ya estamos aquí */ }
            )
        }

        composable<Profile> {
            ProfileScreen(
                onNavigateToSettings = { /* Ajustes */ },
                onMissionClick = { navController.navigate(CreateHabit) },
                onLogClick = { navController.navigate(Log) },
                onSquadClick = { navController.navigate(Friends) }
            )
        }

        // --- FLUJO DE ACCIONES ---

        composable<CreateHabit> {
            AddHabitScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onInitializeMission = {
                    // Al crear, volvemos al Home
                    navController.navigate(Home) {
                        popUpTo(Home) { inclusive = true }
                    }
                }
            )
        }

        composable<HabitView> {
            HabitDetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToEdit = {
                    // Podría navegar a CreateHabit con datos o una pantalla de edición
                }
            )
        }
    }
}
