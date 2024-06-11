package com.example.coursestrack.ui.institution

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coursestrack.data.model.Institution
import com.example.coursestrack.data.repository.InstitutionRepository
import com.example.coursestrack.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InstitutionViewModel @Inject constructor(
    private val institutionRepository: InstitutionRepository
) : ViewModel() {
    private val _institutions = MutableLiveData<UiState<List<Institution>>>()
    val institutions: LiveData<UiState<List<Institution>>>
        get() = _institutions
    fun getAllInstitutions() {
        institutionRepository.getAllInstitutionsByUser() {
            _institutions.value = it
        }

    }
}


