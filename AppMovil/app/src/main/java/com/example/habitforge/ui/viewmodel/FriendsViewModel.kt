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
        // Verificar si ya está marcado en el estado actual para evitar re-procesar
        val alreadyCompleted = _uiState.value.sharedHabits.find { it.id == habitoId }?.completadoHoy == true
        if (alreadyCompleted) return

        viewModelScope.launch {
            // 1. Persistencia en caché centralizada del repositorio para que se mantenga entre pantallas
            habitoRepository.marcarComoCompletadoLocalmente(habitoId)
            
            // 2. Feedback instantáneo en la UI
            _uiState.update { state ->
                state.copy(sharedHabits = state.sharedHabits.map { 
                    if (it.id == habitoId) it.copy(completadoHoy = true) else it 
                })
            }

            // 3. Llamada al servidor
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
