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
import java.time.LocalDate
import java.time.YearMonth

data class HabitDetailUiState(
    val isLoading: Boolean = false,
    val habit: Habito? = null,
    val error: String? = null,
    val currentStreak: Int = 0,
    val bestStreak: Int = 0,
    val totalCompletions: Int = 0,
    val isDeleted: Boolean = false,
    val invitationSuccess: Boolean = false,
    val selectedMonth: YearMonth = YearMonth.now(),
    val completions: List<String> = emptyList() // Lista de fechas en formato ISO
)

class HabitDetailViewModel(
    private val habitoRepository: HabitoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HabitDetailUiState())
    val uiState: StateFlow<HabitDetailUiState> = _uiState.asStateFlow()

    fun cargarHabito(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            when (val result = habitoRepository.obtenerHabitoById(id)) {
                is ApiResult.Success -> {
                    val h = result.data
                    _uiState.update { it.copy(
                        isLoading = false,
                        habit = h,
                        currentStreak = h.rachaGrupalActual,
                        bestStreak = h.rachaGrupalMasLarga,
                        totalCompletions = 0 // Esto debería venir de otro lado o del Habito si tuviera el campo
                    ) }
                    // Cargar historial de cumplimientos si existiera el endpoint
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(
                        isLoading = false,
                        error = result.mensaje
                    ) }
                }
                else -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun onMonthChange(newMonth: YearMonth) {
        _uiState.update { it.copy(selectedMonth = newMonth) }
        // Aquí se llamaría al repositorio para obtener los cumplimientos de ese mes
    }

    fun eliminarHabito() {
        val habitId = _uiState.value.habit?.id ?: return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = habitoRepository.eliminarHabito(habitId)) {
                is ApiResult.Success -> {
                    _uiState.update { it.copy(isLoading = false, isDeleted = true) }
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

    fun invitarAmigo(email: String) {
        val habitId = _uiState.value.habit?.id ?: return
        if (email.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = habitoRepository.invitarAmigos(habitId, listOf(email))) {
                is ApiResult.Success -> {
                    _uiState.update { it.copy(
                        isLoading = false, 
                        invitationSuccess = true,
                        habit = result.data
                    ) }
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

    fun resetInvitationStatus() {
        _uiState.update { it.copy(invitationSuccess = false) }
    }
}
