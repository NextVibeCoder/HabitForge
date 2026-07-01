package com.example.habitforge.ui.viewmodel

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.habitforge.ui.service.ServiceLocator

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            LoginViewModel(
                authRepository = ServiceLocator.provideAuthRepository(),
            )
        }
        initializer {
            HomeViewModel(
                habitoRepository = ServiceLocator.provideHabitoRepository(),
                cumplimientoRepository = ServiceLocator.provideCumplimientoRepository(),
                usuarioRepository = ServiceLocator.provideUsuarioRepository(),
                authRepository = ServiceLocator.provideAuthRepository()
            )
        }
        initializer {
            SignUpViewModel(
                authRepository = ServiceLocator.provideAuthRepository(),
            )
        }
        initializer {
            AddHabitViewModel(
                habitoRepository = ServiceLocator.provideHabitoRepository()
            )
        }
        initializer {
            HabitDetailViewModel(
                habitoRepository = ServiceLocator.provideHabitoRepository(),
                usuarioRepository = ServiceLocator.provideUsuarioRepository(),
                authRepository = ServiceLocator.provideAuthRepository()
            )
        }
        initializer {
            LogViewModel(
                habitoRepository = ServiceLocator.provideHabitoRepository()
            )
        }
        initializer {
            FriendsViewModel(
                habitoRepository = ServiceLocator.provideHabitoRepository(),
                cumplimientoRepository = ServiceLocator.provideCumplimientoRepository(),
                authRepository = ServiceLocator.provideAuthRepository()
            )
        }
        initializer {
            ProfileViewModel(
                usuarioRepository = ServiceLocator.provideUsuarioRepository()
            )
        }
    }
}
