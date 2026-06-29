package com.example.habitforge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitforge.ui.model.Habito
import com.example.habitforge.ui.repository.CumplimientoRepository
import com.example.habitforge.ui.repository.HabitoRepository
import com.example.habitforge.ui.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FriendsUiState(
    val isLoading: Boolean = false,
    val sharedHabits: List<Habito> = emptyList(),
    val pendingInvitations: List<Habito> = emptyList(),
    val error: String? = null
)

class FriendsViewModel(
    private val habitoRepository: HabitoRepository,
    private val cumplimientoRepository: CumplimientoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FriendsUiState())
    val uiState: StateFlow<FriendsUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    fun cargarDatos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val habitosResult = habitoRepository.obtenerHabitosCompartidos()
            val invitacionesResult = habitoRepository.obtenerInvitacionesPendientes()

            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    sharedHabits = if (habitosResult is ApiResult.Success) habitosResult.data else state.sharedHabits,
                    pendingInvitations = if (invitacionesResult is ApiResult.Success) invitacionesResult.data else state.pendingInvitations,
                    error = if (habitosResult is ApiResult.Error) habitosResult.mensaje else null
                )
            }
        }
    }

    fun completarHabito(habitoId: Long) {
        viewModelScope.launch {
            when (val result = cumplimientoRepository.cumplimiento(habitoId)) {
                is ApiResult.Success -> {
                    cargarDatos()
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(error = result.mensaje) }
                }
                else -> {}
            }
        }
    }

    fun aceptarInvitacion(id: Long) {
        viewModelScope.launch {
            when (habitoRepository.aceptarInvitacion(id)) {
                is ApiResult.Success -> cargarDatos()
                is ApiResult.Error -> _uiState.update { it.copy(error = "Error al aceptar invitación") }
                else -> {}
            }
        }
    }

    fun rechazarInvitacion(id: Long) {
        viewModelScope.launch {
            when (habitoRepository.rechazarInvitacion(id)) {
                is ApiResult.Success -> cargarDatos()
                is ApiResult.Error -> _uiState.update { it.copy(error = "Error al rechazar invitación") }
                else -> {}
            }
        }
    }
}
