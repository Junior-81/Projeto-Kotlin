package com.example.coursestrack.data.repository

import com.example.coursestrack.util.UiState

interface AuthRepository {
    fun registerUser(email: String, password: String, result: (UiState<String>) -> Unit)
    fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit)
    fun getSession(result: (id: String?) -> Unit)
    fun logout(result:() -> Unit)
}