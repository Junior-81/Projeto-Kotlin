package com.example.coursestrack.ui.institution

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.coursestrack.adapters.InstitutionAdapter
import com.example.coursestrack.databinding.FragmentInstitutionBinding
import com.example.coursestrack.ui.dialogs.CreationDialog
import com.example.coursestrack.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InstitutionFragment : Fragment() {
    private lateinit var binding: FragmentInstitutionBinding
    private val viewModel: InstitutionViewModel by viewModels()
    private val institutionAdapter = InstitutionAdapter(
        onEditButtonClicked = { institution ->
            // Logic for editing an institution
        },
        onDeleteButtonClicked = { institution ->
            // Logic for deleting an institution
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInstitutionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createInstitutionBtn.setOnClickListener {
            CreationDialog("Adicionar instituição", "instituição") { institutionName ->
                viewModel.createInstitution(institutionName)
            }.show(
                childFragmentManager,
                "createInstitutionDialog"
            )
        }

        binding.recyclerview.adapter = institutionAdapter

        viewModel.getAllInstitutions()

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.institutions.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    // Show loading state
                }

                is UiState.Failure -> {
                    // Handle failure
                }

                is UiState.Success -> {
                    institutionAdapter.setInstitutionsList(state.data)
                }
            }
        }

        viewModel.newInstitution.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    // Show loading state
                }

                is UiState.Failure -> {
                    // Handle failure
                }

                is UiState.Success -> {
                    // Handle success
                }
            }
        }
    }
}
