package com.example.coursestrack.data.repository

import com.example.coursestrack.data.model.Matter
import com.example.coursestrack.util.UiState

interface MatterRepository {
    fun getAllMattersByUser(result: (UiState<List<Matter>>) -> Unit)
    fun createMatter(name: String, result: (UiState<Matter>) -> Unit)
    fun deleteMatter(matterId: String, result: (UiState<String>) -> Unit)
    fun updateMatterName(matter: Matter, newName: String, result: (UiState<String>) -> Unit)
}
