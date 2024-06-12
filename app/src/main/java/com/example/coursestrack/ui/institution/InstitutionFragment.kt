package com.example.coursestrack.ui.institution

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.coursestrack.adapters.InstitutionAdapter
import com.example.coursestrack.databinding.FragmentInstitutionBinding
import com.example.coursestrack.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InstitutionFragment : Fragment() {
    private val viewModel: InstitutionViewModel by viewModels()
    private lateinit var binding: FragmentInstitutionBinding
    private val institutionAdapter = InstitutionAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInstitutionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.recyclerview.adapter=institutionAdapter
    }

    private fun observer() {
        viewModel.institutions.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                }

                is UiState.Failure -> {
                    Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                }

                is UiState.Success -> {
                    institutionAdapter.setInstitutionList(state.data)
                }
            }
        }
    }
}