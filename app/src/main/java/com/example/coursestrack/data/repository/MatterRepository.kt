package com.example.coursestrack.data.repository

import com.example.coursestrack.data.model.Matter
import com.example.coursestrack.util.UiState

interface MatterRepository {
    suspend fun getAllMattersByUser(result: (UiState<List<Matter>>) -> Unit)
    suspend fun createMatter(name: String, result: (UiState<Matter>) -> Unit)
}