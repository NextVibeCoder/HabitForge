package com.example.habitforge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitforge.ui.model.Habito
import com.example.habitforge.ui.repository.AuthRepository
import com.example.habitforge.ui.repository.HabitoRepository
import com.example.habitforge.ui.repository.UsuarioRepository
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
    private val habitoRepository: HabitoRepository,
    private val usuarioRepository: UsuarioRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HabitDetailUiState())
    val uiState: StateFlow<HabitDetailUiState> = _uiState.asStateFlow()

    fun cargarHabito(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val resultHabito = habitoRepository.obtenerHabitoById(id)
            val currentUserId = authRepository.obtenerUsuarioId()
            
            if (resultHabito is ApiResult.Success) {
                val h = resultHabito.data
                
                // 1. Cargamos el historial del perfil (Días pasados)
                val resultPerfil = usuarioRepository.obtenerPerfil()
                val fechasHistorial = if (resultPerfil is ApiResult.Success) {
                    resultPerfil.data.historial
                        .filter { it.nombreHabito == h.nombre }
                        .map { it.fecha }
                } else emptyList()

                // 2. Verificamos si TÚ has completado el hábito HOY (Dato en tiempo real de los participantes)
                val completadoHoyPorMi = h.participantes.any { it.usuarioId == currentUserId && it.completadoHoy }
                val hoyStr = LocalDate.now().toString()
                
                // Combinamos historial y hoy, eliminando duplicados
                val todasLasFechas = (fechasHistorial + if (completadoHoyPorMi) listOf(hoyStr) else emptyList()).distinct()

                _uiState.update { it.copy(
                    isLoading = false,
                    habit = h,
                    currentStreak = h.rachaGrupalActual,
                    bestStreak = h.rachaGrupalMasLarga,
                    completions = todasLasFechas,
                    totalCompletions = todasLasFechas.size
                ) }

            } else if (resultHabito is ApiResult.Error) {
                _uiState.update { it.copy(isLoading = false, error = resultHabito.mensaje) }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onMonthChange(newMonth: YearMonth) {
        _uiState.update { it.copy(selectedMonth = newMonth) }
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
