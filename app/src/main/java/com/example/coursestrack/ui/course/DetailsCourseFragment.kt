package com.example.coursestrack.ui.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import calculateProgressPercentage
import com.example.coursestrack.MainActivity
import com.example.coursestrack.R
import com.example.coursestrack.databinding.FragmentDetailsCourseBinding
import com.example.coursestrack.util.limitTitleLength
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsCourseFragment : Fragment() {
    private val args: DetailsCourseFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsCourseBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsCourseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).updateToolbarTitle(args.courseData.name.limitTitleLength(15))
        binding.durationLabel.text =
            "${args.courseData.durationType} totais: ${args.courseData.duration}"
        binding.progressLabel.text =
            "${args.courseData.durationType} assistidas: ${args.courseData.progress}"

        val progressPerCent =
            calculateProgressPercentage(args.courseData.duration, args.courseData.progress)
        binding.progress.progress = progressPerCent
        binding.progressPerCent.text = "$progressPerCent %"

        binding.matter.text = args.courseData.matterName
        binding.institution.text = args.courseData.institutionName

        binding.updateProgressBtn.setOnClickListener {
            Toast.makeText(context, "Atualizar progresso", Toast.LENGTH_SHORT)
        }

        binding.deleteCourseBtn.setOnClickListener {
            Toast.makeText(context, "Excluir Curso", Toast.LENGTH_SHORT)
        }

        binding.editCourseBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("courseData", args.courseData)
            findNavController().navigate(R.id.action_detailsCourseFragment_to_editCourseFragment, bundle)
        }
    }
}