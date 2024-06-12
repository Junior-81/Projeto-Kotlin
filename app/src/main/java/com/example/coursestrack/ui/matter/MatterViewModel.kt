package com.example.coursestrack.ui.matter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coursestrack.data.model.Matter
import com.example.coursestrack.data.repository.CourseRepository
import com.example.coursestrack.data.repository.InstitutionRepository
import com.example.coursestrack.data.repository.MatterRepository
import com.example.coursestrack.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatterViewModel @Inject constructor(
    private val matterRepository: MatterRepository,
) :
    ViewModel() {
    private val _matters = MutableLiveData<UiState<List<Matter>>>()
    val matters: LiveData<UiState<List<Matter>>>
        get() = _matters

    private val _newMatter = MutableLiveData<UiState<Matter>>()
    val newMatter: LiveData<UiState<Matter>>
        get() = _newMatter

    fun getAllMatters() {
        matterRepository.getAllMattersByUser() {
            _matters.value = it
        }
    }
    fun createMatter(name: String) {
        _newMatter.value = UiState.Loading
        matterRepository.createMatter(name) {
            _newMatter.value = it
            getAllMatters()
        }
    }

}