package com.example.coursestrack.ui.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursestrack.data.model.Institution
import com.example.coursestrack.data.model.Matter
import com.example.coursestrack.data.repository.InstitutionRepository
import com.example.coursestrack.data.repository.MatterRepository
import com.example.coursestrack.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCourseViewModel @Inject constructor(
    private val matterRepository: MatterRepository,
    private val institutionRepository: InstitutionRepository
) :
    ViewModel() {
    private val _matters = MutableLiveData<UiState<List<Matter>>>()
    val matters: LiveData<UiState<List<Matter>>>
        get() = _matters

    private val _institutions = MutableLiveData<UiState<List<Institution>>>()
    val institutions: LiveData<UiState<List<Institution>>>
        get() = _institutions

    private val _newInstitution = MutableLiveData<UiState<Institution>>()
    val newInstitution: LiveData<UiState<Institution>>
        get() = _newInstitution

    private val _newMatter = MutableLiveData<UiState<Matter>>()
    val newMatter: LiveData<UiState<Matter>>
        get() = _newMatter

    init {
        getAllMatters()
        getAllInstitutions()
    }

    private fun getAllMatters() {
        viewModelScope.launch {
            matterRepository.getAllMattersByUser() {
                _matters.value = it
            }
        }
    }

    private fun getAllInstitutions() {
        viewModelScope.launch {
            institutionRepository.getAllInstitutionsByUser() {
                _institutions.value = it
            }
        }
    }

    fun createInstitution(name: String) {
        _newInstitution.value = UiState.Loading
        viewModelScope.launch {
            institutionRepository.createInstitution(name) {
                _newInstitution.value = it
                getAllInstitutions()
            }
        }
    }

    fun createMatter(name: String) {
        _newMatter.value = UiState.Loading
        viewModelScope.launch {
            matterRepository.createMatter(name) {
                _newMatter.value = it
                getAllMatters()
            }
        }
    }

    fun createCourse(
        name: String,
        durationType: String,
        duration: Number,
        matter: String,
        institution: String
    ) {
        Log.d("data-course", "$name $durationType $duration $matter $institution")
    }
}