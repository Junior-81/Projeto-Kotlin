package com.example.coursestrack.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coursestrack.data.models.Course
import com.example.coursestrack.data.repository.CourseRepository
import com.example.coursestrack.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: CourseRepository) : ViewModel() {
    private val _coursesList = MutableLiveData<UiState<List<Course>>>()
    val coursesList: LiveData<UiState<List<Course>>> get() = _coursesList

    fun getCourseList() {
        _coursesList.value = UiState.Loading

        repository.getCourses { result ->
            _coursesList.value = result
        }
    }


}