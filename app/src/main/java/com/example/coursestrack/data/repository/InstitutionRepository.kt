package com.example.coursestrack.data.repository

import com.example.coursestrack.data.model.Institution
import com.example.coursestrack.util.UiState

interface InstitutionRepository {
    suspend fun getAllInstitutionsByUser(result: (UiState<List<Institution>>) -> Unit)
    suspend fun createInstitution(name: String, result: (UiState<Institution>) -> Unit)
}