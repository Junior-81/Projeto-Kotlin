package com.example.coursestrack.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.coursestrack.R
import com.example.coursestrack.adapters.CustomArrayAdapter
import com.example.coursestrack.databinding.FragmentCreateCourseBinding
import com.example.coursestrack.ui.dialogs.CreationDialog
import com.example.coursestrack.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCourseFragment : Fragment() {
    lateinit var binding: FragmentCreateCourseBinding
    val viewModel: CreateCourseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateCourseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()

        binding.createCourseBtn.setOnClickListener {
            val courseName = binding.courseNameInput.text.toString()
            val courseDurationType = getWorkloadSelected()
            val courseDuration = binding.courseDurationInput.text.toString().toInt()
            val matter = binding.courseMatterInput.text.toString()
            val institution = binding.courseInstitutionInput.text.toString()

            viewModel.createCourse(
                courseName,
                courseDurationType,
                courseDuration,
                matter,
                institution
            )
        }

        binding.createMatterBtn.setOnClickListener {
            val createMatterDialog = CreationDialog("Adicionar matéria", "matéria") {
                viewModel.createMatter(it)
            }
            createMatterDialog.show(
                childFragmentManager,
                "createMatterDialog"
            )
        }

        binding.createInstitutionBtn.setOnClickListener {
            val createInstitutionDialog = CreationDialog("Adicionar instituição", "instituição") {
                viewModel.createInstitution(it)
            }
            createInstitutionDialog.show(
                childFragmentManager,
                "createInstitutionDialog"

            )
        }
    }

    private fun observer() {
        viewModel.matters.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.courseMatterLayout.isEnabled = false
                }

                is UiState.Failure -> {
                    binding.courseMatterLayout.isEnabled = true
                    Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                }

                is UiState.Success -> {
                    binding.courseMatterLayout.isEnabled = true
                    val matterArrayAdapter = CustomArrayAdapter(
                        requireContext(),
                        R.layout.input_list_item,
                        state.data.map { it.name }.toTypedArray()
                    )
                    binding.courseMatterInput.setAdapter(matterArrayAdapter)
                }
            }
        }

        viewModel.institutions.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.courseInstitutionLayout.isEnabled = false
                    Toast.makeText(context, "carregando", Toast.LENGTH_SHORT).show()
                }

                is UiState.Failure -> {
                    binding.courseInstitutionLayout.isEnabled = true
                    Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                }

                is UiState.Success -> {
                    binding.courseInstitutionLayout.isEnabled = true
                    val institutionsArrayAdapter = CustomArrayAdapter(
                        requireContext(),
                        R.layout.input_list_item,
                        state.data.map { it.name }.toTypedArray()
                    )
                    binding.courseInstitutionInput.setAdapter(institutionsArrayAdapter)
                }
            }
        }
    }

    private fun getWorkloadSelected(): String {
        var workloadTypeSelected = ""
        val selectedRadioButtonId = binding.courseWorkloadOptions.checkedRadioButtonId

        if (selectedRadioButtonId != -1) {
            val selectedWorkload =
                view?.findViewById<RadioButton>(selectedRadioButtonId)?.text.toString()
            workloadTypeSelected = selectedWorkload
        }
        return workloadTypeSelected
    }
}