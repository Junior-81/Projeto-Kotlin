package com.example.coursestrack.ui.institution

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursestrack.data.model.Institution
import com.example.coursestrack.data.repository.InstitutionRepository
import com.example.coursestrack.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstitutionViewModel @Inject constructor(
    private val institutionRepository: InstitutionRepository
) : ViewModel() {

    private val _institutions = MutableLiveData<UiState<List<Institution>>>()
    val institutions: LiveData<UiState<List<Institution>>>
        get() = _institutions

    private val _newInstitution = MutableLiveData<UiState<Institution>>()
    val newInstitution: LiveData<UiState<Institution>>
        get() = _newInstitution

    fun getAllInstitutions() {
        institutionRepository.getAllInstitutionsByUser() {
            _institutions.value = it
        }

    }
    fun createInstitution(name: String) {
        _newInstitution.value = UiState.Loading
        institutionRepository.createInstitution(name) {
            _newInstitution.value = it
            getAllInstitutions()
        }
    }
}
