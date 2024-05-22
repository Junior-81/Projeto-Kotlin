package com.example.coursestrack.data.repository

import com.example.coursestrack.data.models.Course
import com.example.coursestrack.data.models.CursoMockData
import com.example.coursestrack.util.UiState

class CourseRepositoryFirebase: CourseRepository {
    override fun getCourses(result: (UiState<List<Course>>) -> Unit) {
        val cursos = CursoMockData.getCursosMockados()
        result(UiState.Success(cursos))
    }
}