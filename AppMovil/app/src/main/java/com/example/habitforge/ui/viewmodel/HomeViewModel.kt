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

    fun cargarHabitos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
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
        viewModelScope.launch {
            when (val result = cumplimientoRepository.cumplimiento(habitoId)) {
                is ApiResult.Success -> {
                    cargarHabitos()
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(error = result.mensaje) }
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
