package com.example.coursestrack.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coursestrack.R
import com.example.coursestrack.databinding.FragmentHomeBinding
import com.example.coursestrack.ui.auth.AuthViewModel
import com.example.coursestrack.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment() {
    val authViewModel: AuthViewModel by viewModels()
    val homeViewModel: HomeViewModel by viewModels()
    val TAG: String = "HomeFragment"
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
        observer()

        binding.logoutBtn.setOnClickListener {
            authViewModel.logout {
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }
        }

        binding.createCourseBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createCourseFragment)
        }
    }

    private fun observer() {
        homeViewModel.coursesList.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Loading -> {
                    Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                }
                is UiState.Failure -> {
                    Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                }
                is UiState.Success -> {
                    Log.d(TAG, state.data.toString())

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getCourseList()
    }

}
