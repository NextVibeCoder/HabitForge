package com.example.habitforge.ui.service

import android.content.Context
import android.content.SharedPreferences

class SessionManager (context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun guardarToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    fun obtenerToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }

    fun guardarUsuarioId(id: Long) {
        prefs.edit().putLong(KEY_USUARIO_ID, id).apply()
    }

    fun obtenerUsuarioId(): Long? {
        val id = prefs.getLong(KEY_USUARIO_ID, -1L)
        return if (id == -1L) null else id
    }

    fun limpiarSesion() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val PREFS_NAME = "habitforge_session"
        private const val KEY_TOKEN = "token"
        private const val KEY_USUARIO_ID = "usuario_id"
    }
}