package com.example.coursestrack.ui.create

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.example.coursestrack.databinding.FragmentCreateCourseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCourseFragment : Fragment() {
    lateinit var binding: FragmentCreateCourseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateCourseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createCourseBtn.setOnClickListener {
            val courseName = binding.courseNameInput.text.toString()
            val courseWorkload = getWorkloadSelected()
            val courseDuration = binding.courseDurationInput.text.toString()
            val matter = binding.courseMatterInput.text.toString()
            val institution = binding.courseInstitutionInput.text.toString()

            Log.d(
                "course-data",
                "nome: $courseName, tipo: $courseWorkload, tempo: $courseDuration, mat: $matter, inst: $institution"
            )
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