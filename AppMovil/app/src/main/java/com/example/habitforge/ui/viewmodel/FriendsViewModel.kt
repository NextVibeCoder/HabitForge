package com.example.habitforge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitforge.ui.model.Habito
import com.example.habitforge.ui.repository.AuthRepository
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
    val userId: Long? = null,
    val error: String? = null
)

class FriendsViewModel(
    private val habitoRepository: HabitoRepository,
    private val cumplimientoRepository: CumplimientoRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FriendsUiState())
    val uiState: StateFlow<FriendsUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(userId = authRepository.obtenerUsuarioId()) }
        cargarDatos()
    }

    fun cargarDatos(silent: Boolean = false) {
        viewModelScope.launch {
            if (!silent) _uiState.update { it.copy(isLoading = true, error = null) }
            
            val habitosResult = habitoRepository.obtenerHabitosCompartidos()

            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    sharedHabits = if (habitosResult is ApiResult.Success) habitosResult.data else state.sharedHabits,
                    error = if (habitosResult is ApiResult.Error) habitosResult.mensaje else null
                )
            }
        }
    }

    fun completarHabito(habitoId: Long) {
        val habito = _uiState.value.sharedHabits.find { it.id == habitoId } ?: return
        val currentUserId = _uiState.value.userId ?: return
        
        val completadoPorUsuario = habito.participantes.any { 
            it.usuarioId == currentUserId && it.completadoHoy 
        }
        
        if (completadoPorUsuario || !habito.esDiaObligatorio) return

        viewModelScope.launch {
            when (val result = cumplimientoRepository.cumplimiento(habitoId)) {
                is ApiResult.Success -> {
                    cargarDatos(silent = true)
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(error = result.mensaje) }
                }
                else -> {}
            }
        }
    }
}
