package com.example.habitforge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitforge.ui.model.Habito
import com.example.habitforge.ui.repository.CumplimientoRepository
import com.example.habitforge.ui.repository.HabitoRepository
import com.example.habitforge.ui.repository.UsuarioRepository
import com.example.habitforge.ui.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val isLoading: Boolean = false,
    val habitos: List<Habito> = emptyList(),
    val error: String? = null,
    val userName: String = "",
    val nivel: Int = 0,
    val rango: String = "",
    val xpActual: Int = 0,
    val xpTotal: Int = 1
)

class HomeViewModel(
    private val habitoRepository: HabitoRepository,
    private val cumplimientoRepository: CumplimientoRepository,
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        cargarHabitos()
        cargarNombreUsuario()
    }

    fun cargarNombreUsuario() {
        viewModelScope.launch {
            when (val result = usuarioRepository.obtenerPerfil()) {
                is ApiResult.Success -> {
                    _uiState.update { it.copy(userName = result.data.username) }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(error = result.mensaje) }
                }
                else -> {}
            }
        }
    }

    fun cargarHabitos(silent: Boolean = false) {
        viewModelScope.launch {
            if (!silent) _uiState.update { it.copy(isLoading = true, error = null) }
            
            when (val result = habitoRepository.obtenerHabitosIndividuales()) {
                is ApiResult.Success -> {
                    _uiState.update { it.copy(
                        isLoading = false,
                        habitos = result.data
                    ) }
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

    fun completarHabito(habitoId: Long, fecha: String) {
        // Evitar múltiples clicks si ya está marcado
        val alreadyCompleted = _uiState.value.habitos.find { it.id == habitoId }?.completadoHoy == true
        if (alreadyCompleted) return

        viewModelScope.launch {
            // Actualización optimista del estado para solucionar el bug visual (feedback instantáneo)
            _uiState.update { state ->
                state.copy(habitos = state.habitos.map { 
                    if (it.id == habitoId) it.copy(completadoHoy = true) else it 
                })
            }
            
            when (val result = cumplimientoRepository.cumplimiento(habitoId)) {
                is ApiResult.Success -> {
                    // Carga silenciosa para mantener el estado visual sin parpadeos de carga
                    cargarHabitos(silent = true)
                }
                is ApiResult.Error -> {
                    // Revertir en caso de error
                    _uiState.update { state ->
                        state.copy(
                            habitos = state.habitos.map { 
                                if (it.id == habitoId) it.copy(completadoHoy = false) else it 
                            },
                            error = result.mensaje
                        )
                    }
                }
                else -> {}
            }
        }
    }
    
    fun eliminarHabito(id: Long) {
        viewModelScope.launch {
            when (val result = habitoRepository.eliminarHabito(id)) {
                is ApiResult.Success -> cargarHabitos()
                is ApiResult.Error -> _uiState.update { it.copy(error = "Error al eliminar: ${result.mensaje}") }
                else -> {}
            }
        }
    }
}
