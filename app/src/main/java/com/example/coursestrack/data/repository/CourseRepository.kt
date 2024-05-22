package com.example.coursestrack.data.repository

import com.example.coursestrack.data.models.Course
import com.example.coursestrack.util.UiState

interface CourseRepository {
    fun getCourses(result: (UiState<List<Course>>) -> Unit)
}