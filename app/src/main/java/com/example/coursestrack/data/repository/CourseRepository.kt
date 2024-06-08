package com.example.coursestrack.data.repository

import com.example.coursestrack.data.model.Course
import com.example.coursestrack.data.model.Institution
import com.example.coursestrack.data.model.Matter
import com.example.coursestrack.util.UiState

interface CourseRepository {
    fun createCourse(
        course: Course,
        institution: Institution,
        matter: Matter,
        result: (UiState<Course>) -> Unit
    )

    fun getAllCourses(result: (UiState<List<Course>>) -> Unit)
}