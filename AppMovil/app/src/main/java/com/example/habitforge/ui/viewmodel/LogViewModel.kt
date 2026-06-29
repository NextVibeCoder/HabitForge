package com.example.habitforge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitforge.ui.model.Habito
import com.example.habitforge.ui.repository.HabitoRepository
import com.example.habitforge.ui.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LogUiState(
    val isLoading: Boolean = false,
    val invitations: List<Habito> = emptyList(),
    val error: String? = null
)

class LogViewModel(
    private val habitoRepository: HabitoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LogUiState())
    val uiState: StateFlow<LogUiState> = _uiState.asStateFlow()

    init {
        cargarInvitaciones()
    }

    fun cargarInvitaciones() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = habitoRepository.obtenerInvitacionesPendientes()) {
                is ApiResult.Success -> {
                    _uiState.update { it.copy(isLoading = false, invitations = result.data) }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.mensaje) }
                }
                else -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun aceptarInvitacion(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = habitoRepository.aceptarInvitacion(id)) {
                is ApiResult.Success -> {
                    cargarInvitaciones()
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.mensaje) }
                }
                else -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun rechazarInvitacion(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = habitoRepository.rechazarInvitacion(id)) {
                is ApiResult.Success -> {
                    cargarInvitaciones()
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.mensaje) }
                }
                else -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}
