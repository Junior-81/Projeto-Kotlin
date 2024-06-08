package com.example.coursestrack.ui.home

import android.util.Log
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
    private val courseRepository: CourseRepository,
    private val institutionRepository: InstitutionRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _coursesList = MutableLiveData<UiState<List<Course>>>()
    val coursesList: LiveData<UiState<List<Course>>>
        get() = _coursesList

    private val _institutions = MutableLiveData<UiState<List<Institution>>>()
    val institutions: LiveData<UiState<List<Institution>>>
        get() = _institutions

    fun getAllCourses() {
        _coursesList.value = UiState.Loading
        courseRepository.getAllCourses {
            Log.d("test","os dados: $it")
            _coursesList.value = it
        }
    }

    fun getAllInstitutions() {
        institutionRepository.getAllInstitutionsByUser() {
            _institutions.value = it
        }

    }

    fun getUserSession(result: (id: String?) -> Unit) {
        authRepository.getSession(result)
    }

    fun logout(result: () -> Unit) {
        authRepository.logout(result)
    }
}