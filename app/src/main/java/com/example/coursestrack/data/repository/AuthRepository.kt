package com.example.coursestrack.data.repository

import com.example.coursestrack.util.UiState

interface AuthRepository {
    fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit)
}