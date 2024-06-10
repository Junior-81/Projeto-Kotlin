package com.example.coursestrack.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coursestrack.R
import com.example.coursestrack.adapters.CourseAdapter
import com.example.coursestrack.databinding.FragmentHomeBinding
import com.example.coursestrack.ui.dialogs.UpdateProgressDialog
import com.example.coursestrack.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    val viewModel: HomeViewModel by viewModels()
    lateinit var binding: FragmentHomeBinding
    private val courseAdapter = CourseAdapter(
        onProgressButtonClicked = { course ->
            UpdateProgressDialog(course) {
                viewModel.updateProgress(course, it)
            }.show(parentFragmentManager, "progressDialog")
        },
        onDetailsButtonClicked = { course ->
            val bundle = Bundle()
            bundle.putParcelable("courseData", course)
            findNavController().navigate(R.id.action_homeFragment_to_detailsCourseFragment, bundle)
        })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()

        binding.coursesRecycleView.adapter = courseAdapter
        binding.logoutBtn.setOnClickListener {
            viewModel.logout {
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }
        }

        binding.createCourseBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createCourseFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getUserSession { id ->
            if (id == null) {
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.getAllCourses()

    }

    private fun observer() {
        viewModel.coursesList.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.loading.root.visibility = View.VISIBLE
                }

                is UiState.Failure -> {
                    Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                    binding.loading.root.visibility = View.GONE
                }

                is UiState.Success -> {
                    binding.loading.root.visibility = View.GONE
                    courseAdapter.setCoursesList(state.data)
                }
            }
        }

        viewModel.updateProgress.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.loading.root.visibility = View.VISIBLE
                }

                is UiState.Failure -> {
                    Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
                    binding.loading.root.visibility = View.GONE
                }

                is UiState.Success -> {
                    binding.loading.root.visibility = View.GONE
                }
            }
        }


    }
}