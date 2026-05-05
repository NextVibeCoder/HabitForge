package com.example.habitforge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habitforge.ui.screen.*

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SignIn) {

        composable<SignIn> {
            SignIn(
                onNavigateToRegister = { navController.navigate(SignUp) },
                onSignInSuccess = {
                    navController.navigate(Home) {
                        popUpTo(SignIn) { inclusive = true }
                    }
                }
            )
        }

        composable<SignUp> {
            SignUpScreen(
                onNavigateToSignIn = { navController.navigate(SignIn) },
                onSignUpSuccess = {
                    navController.navigate(Home) {
                        popUpTo(SignUp) { inclusive = true }
                    }
                }
            )
        }

        composable<Home> {
            Home(
                onNavigateToCreateHabit = { navController.navigate(CreateHabit) },
                onNavigateToProfile = { navController.navigate(Profile) },
                onNavigateToSquad = { navController.navigate(Friends) },
                onNavigateToLog = { navController.navigate(Log) },
                onNavigateToHabitDetail = { navController.navigate(HabitView) },
                onNavigateToHome = { /* Ya aquí */ }
            )
        }

        composable<Log> {
            LogScreen(
                onNavigateToHome = { navController.navigate(Home) },
                onNavigateToSquad = { navController.navigate(Friends) },
                onNavigateToProfile = { navController.navigate(Profile) }
            )
        }

        composable<Friends> {
            FriendsScreen(
                onNavigateToHome = { navController.navigate(Home) },
                onNavigateToLog = { navController.navigate(Log) },
                onNavigateToProfile = { navController.navigate(Profile) },
                onNavigateToSquad = { /* Ya aquí */ }
            )
        }

        composable<Profile> {
            ProfileScreen(
                onNavigateToHome = { navController.navigate(Home) }, // CORREGIDO: Redirige a Home
                onNavigateToLog = { navController.navigate(Log) },
                onNavigateToSquad = { navController.navigate(Friends) },
                onLogout = {
                    navController.navigate(SignIn) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable<CreateHabit> {
            AddHabitScreen(
                onNavigateBack = { navController.popBackStack() },
                onInitializeMission = {
                    navController.navigate(Home) {
                        popUpTo(Home) { inclusive = true }
                    }
                }
            )
        }

        composable<HabitView> {
            HabitDetailScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
