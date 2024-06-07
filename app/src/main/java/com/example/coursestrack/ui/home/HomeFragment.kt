package com.example.coursestrack.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import com.example.coursestrack.R
import com.example.coursestrack.data.model.Course
import com.example.coursestrack.databinding.FragmentHomeBinding
import com.example.coursestrack.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment() {
    val viewModel: AuthViewModel by viewModels()
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutBtn.setOnClickListener {
            viewModel.logout {
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }
        }

        binding.createCourseBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createCourseFragment)
        }

        binding.editCourseBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("courseData", Course())
            findNavController().navigate(R.id.action_homeFragment_to_editCourseFragment, bundle)
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
}