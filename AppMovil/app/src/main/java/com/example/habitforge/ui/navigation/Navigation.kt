package com.example.habitforge.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.habitforge.ui.screen.*

@Composable
fun Navigation() {
    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        NavHost(
            navController = navController,
            startDestination = SignIn,
            modifier = Modifier.fillMaxSize(),
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) },
            popEnterTransition = { fadeIn(animationSpec = tween(700)) },
            popExitTransition = { fadeOut(animationSpec = tween(700)) }
        ) {
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
                        navController.navigate(SignIn) {
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
                    onNavigateToHabitDetail = { id -> navController.navigate(HabitView(id)) },
                    onNavigateToHome = { }
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
                    onNavigateToSquad = { }
                )
            }

            composable<Profile> {
                ProfileScreen(
                    onNavigateToHome = { navController.navigate(Home) },
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

            composable<HabitView> { backStackEntry ->
                val habitView: HabitView = backStackEntry.toRoute()
                HabitDetailScreen(
                    habitId = habitView.id,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToEdit = { id -> navController.navigate(EditHabit(id)) }
                )
            }

            composable<EditHabit> { backStackEntry ->
                val editHabit: EditHabit = backStackEntry.toRoute()
                AddHabitScreen(
                    habitId = editHabit.id,
                    onNavigateBack = { navController.popBackStack() },
                    onInitializeMission = {
                        navController.navigate(Home) {
                            popUpTo(Home) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
