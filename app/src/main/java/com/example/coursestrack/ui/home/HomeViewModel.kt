package com.example.coursestrack.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coursestrack.data.model.Course
import com.example.coursestrack.data.model.Institution
import com.example.coursestrack.data.repository.AuthRepository
import com.example.coursestrack.data.repository.CourseRepository
import com.example.coursestrack.data.repository.InstitutionRepository
import com.example.coursestrack.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val courseRepository: CourseRepository
) : ViewModel() {
    private val _coursesList = MutableLiveData<UiState<List<Course>>>()
    val coursesList: LiveData<UiState<List<Course>>>
        get() = _coursesList


    private val _updateProgress = MutableLiveData<UiState<String>>()
    val updateProgress: LiveData<UiState<String>>
        get() = _updateProgress

    fun getAllCourses(userId: String) {
        _coursesList.value = UiState.Loading
        courseRepository.getAllCourses(userId) {
            _coursesList.value = it
        }
    }

    fun updateProgress(course: Course, progress: Long) {
        _updateProgress.value = UiState.Loading
        courseRepository.updateCourseProgress(course, progress) {
            _updateProgress.value = it
        }
    }
}