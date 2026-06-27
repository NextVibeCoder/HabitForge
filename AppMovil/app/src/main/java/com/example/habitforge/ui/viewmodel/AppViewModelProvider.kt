package com.example.habitforge.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.habitforge.ui.service.ServiceLocator

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            LoginViewModel(
                authRepository = ServiceLocator.provideAuthRepository()
            )
        }
    }
}
