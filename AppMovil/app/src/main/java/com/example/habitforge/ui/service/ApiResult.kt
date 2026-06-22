package com.example.habitforge.ui.service

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()

    data class Error(val mensaje: String, val codigo: Int? = null) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}