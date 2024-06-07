package com.example.coursestrack.ui.create

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.coursestrack.R
import com.example.coursestrack.adapters.CustomArrayAdapter
import com.example.coursestrack.data.model.Institution
import com.example.coursestrack.data.model.Matter
import com.example.coursestrack.databinding.FragmentCreateCourseBinding
import com.example.coursestrack.ui.dialogs.CreationDialog
import com.example.coursestrack.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCourseFragment : Fragment() {
    private val args: EditCourseFragmentArgs by navArgs()
    private lateinit var binding: FragmentCreateCourseBinding
    private val viewModel: CreateCourseViewModel by viewModels()
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

        binding.createCourseBtn.isEnabled = false // Desabilita o botão inicialmente

        // Adiciona TextWatchers
        binding.courseNameInput.addTextChangedListener(textWatcher)
        binding.courseDurationInput.addTextChangedListener(textWatcher)
        binding.courseMatterInput.addTextChangedListener(textWatcher)
        binding.courseInstitutionInput.addTextChangedListener(textWatcher)

        // Adiciona listener ao RadioGroup
        binding.courseWorkloadOptions.setOnCheckedChangeListener { _, _ ->
            validateFields()
        }


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
                        validateFields()
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
                    binding.courseInstitutionLayout.isEnabled = true
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
                        validateFields()
                    }
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

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    private fun validateFields() {
        val isNameFilled = binding.courseNameInput.text.toString().isNotEmpty()
        val isDurationFilled = binding.courseDurationInput.text.toString().isNotEmpty()
        val isMatterSelected = selectedMatter != null
        val isInstitutionSelected = selectedInstitution != null
        val isWorkloadSelected = binding.courseWorkloadOptions.checkedRadioButtonId != -1

        binding.createCourseBtn.isEnabled = isNameFilled && isDurationFilled && isMatterSelected && isInstitutionSelected && isWorkloadSelected
    }
}
