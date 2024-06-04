package com.example.coursestrack.data.repository

import com.example.coursestrack.data.model.Matter
import com.example.coursestrack.util.UiState

interface MatterRepository {
    fun getAllMattersByUser(result: (UiState<List<Matter>>) -> Unit)
    fun createMatter(name: String, result: (UiState<Matter>) -> Unit)
}