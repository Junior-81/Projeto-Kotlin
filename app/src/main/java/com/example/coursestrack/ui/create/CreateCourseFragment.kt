package com.example.coursestrack.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coursestrack.R
import com.example.coursestrack.adapters.CustomArrayAdapter
import com.example.coursestrack.data.model.Institution
import com.example.coursestrack.data.model.Matter
import com.example.coursestrack.databinding.FragmentCreateCourseBinding
import com.example.coursestrack.ui.dialogs.CreationDialog
import com.example.coursestrack.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCourseFragment : Fragment() {
    lateinit var binding: FragmentCreateCourseBinding
    val viewModel: CreateCourseViewModel by viewModels()
    private var selectedInstitution: Institution? = null
    private var selectedMatter: Matter? = null

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

            viewModel.createCourse(
                courseName,
                courseDurationType,
                courseDuration,
                selectedMatter!!,
                selectedInstitution!!
            )
        }

        binding.createMatterBtn.setOnClickListener {
            CreationDialog("Adicionar matéria", "matéria") {
                viewModel.createMatter(it)
            }.show(
                childFragmentManager,
                "createMatterDialog"
            )
        }

        binding.createInstitutionBtn.setOnClickListener {
            CreationDialog("Adicionar instituição", "instituição") {
                viewModel.createInstitution(it)
            }.show(
                childFragmentManager,
                "createInstitutionDialog"
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllMatters()
        viewModel.getAllInstitutions()
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
                    val matterArray = state.data.toTypedArray()
                    val matterArrayAdapter = CustomArrayAdapter(
                        requireContext(),
                        R.layout.input_list_item,
                        state.data.map { it.name }.toTypedArray()
                    )
                    binding.courseMatterInput.setAdapter(matterArrayAdapter)

                    binding.courseMatterInput.setOnItemClickListener { _, _, position, _ ->
                        selectedMatter = matterArray[position]
                    }
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
                    binding.courseInstitutionLayout.isEnabled = false
                    Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                }

                is UiState.Success -> {
                    binding.courseInstitutionLayout.isEnabled = true
                    val institutionArray = state.data.toTypedArray()
                    val institutionsArrayAdapter = CustomArrayAdapter(
                        requireContext(),
                        R.layout.input_list_item,
                        state.data.map { it.name }.toTypedArray()
                    )
                    binding.courseInstitutionInput.setAdapter(institutionsArrayAdapter)

                    binding.courseInstitutionInput.setOnItemClickListener { _, _, position, _ ->
                        selectedInstitution = institutionArray[position]
                    }
                }
            }
        }

        viewModel.newCourse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.createCourseBtn.text = ""
                    binding.btnProgress.show()
                }

                is UiState.Failure -> {
                    Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                }

                is UiState.Success -> {
                    Toast.makeText(context, "Curso criado com sucesso", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
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