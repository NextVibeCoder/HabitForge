package com.example.habitforge.ui.viewmodel

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.habitforge.ui.repository.AuthRepository
import com.example.habitforge.ui.repository.HabitoRepository
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
            )
        }
        initializer {
            SignUpViewModel(
                authRepository = ServiceLocator.provideAuthRepository(),
            )
        }
    }
}
