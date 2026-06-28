package com.example.habitforge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitforge.ui.model.enums.DiaSemana
import com.example.habitforge.ui.model.enums.FrecuenciaTipo
import com.example.habitforge.ui.repository.HabitoRepository
import com.example.habitforge.ui.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AddHabitUiState(
    val nombre: String = "",
    val descripcion: String = "",
    val frecuencia: FrecuenciaTipo = FrecuenciaTipo.DIARIA,
    val icon: String = "🎯",
    val esCompartido: Boolean = false,
    val diasSemana: List<DiaSemana> = emptyList(),
    val amigosInvitados: List<String> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val habitCreated: Boolean = false
)

class AddHabitViewModel(
    private val habitoRepository: HabitoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddHabitUiState())
    val uiState: StateFlow<AddHabitUiState> = _uiState.asStateFlow()

    fun onNombreChange(nombre: String) {
        _uiState.update { it.copy(nombre = nombre) }
    }

    fun onDescripcionChange(descripcion: String) {
        _uiState.update { it.copy(descripcion = descripcion) }
    }

    fun onFrecuenciaChange(frecuencia: FrecuenciaTipo) {
        _uiState.update { 
            it.copy(
                frecuencia = frecuencia,
                diasSemana = if (frecuencia == FrecuenciaTipo.DIARIA) emptyList() else it.diasSemana
            ) 
        }
    }

    fun toggleDiaSemana(dia: DiaSemana) {
        _uiState.update { state ->
            val nuevosDias = if (state.diasSemana.contains(dia)) {
                state.diasSemana - dia
            } else {
                state.diasSemana + dia
            }
            state.copy(diasSemana = nuevosDias)
        }
    }

    fun onIconChange(icon: String) {
        _uiState.update { it.copy(icon = icon) }
    }

    fun onTipoChange(esCompartido: Boolean) {
        _uiState.update { it.copy(esCompartido = esCompartido) }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun addAmigo(email: String) {
        if (email.isNotBlank() && !_uiState.value.amigosInvitados.contains(email)) {
            _uiState.update { it.copy(amigosInvitados = it.amigosInvitados + email) }
        }
    }

    fun removeAmigo(email: String) {
        _uiState.update { it.copy(amigosInvitados = it.amigosInvitados - email) }
    }

    fun crearHabito() {
        viewModelScope.launch {
            val state = _uiState.value
            if (state.nombre.isBlank()) {
                _uiState.update { it.copy(error = "El nombre es obligatorio") }
                return@launch
            }
            
            if (state.frecuencia == FrecuenciaTipo.SEMANAL && state.diasSemana.isEmpty()) {
                _uiState.update { it.copy(error = "Selecciona al menos un día para la frecuencia semanal") }
                return@launch
            }

            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val result = habitoRepository.crearHabito(
                nombre = state.nombre,
                descripcion = state.descripcion,
                frecuencia = state.frecuencia,
                icon = state.icon,
                esCompartido = state.esCompartido,
                diasSemana = state.diasSemana,
                amigosInvitados = state.amigosInvitados
            )

            when (result) {
                is ApiResult.Success -> {
                    _uiState.update { it.copy(isLoading = false, habitCreated = true) }
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
    
    fun resetHabitCreated() {
        _uiState.update { it.copy(habitCreated = false) }
    }
}
