package com.example.coursestrack.ui.institution

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coursestrack.databinding.FragmentInstitutionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InstitutionFragment : Fragment() {
    private lateinit var binding: FragmentInstitutionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInstitutionBinding.inflate(layoutInflater)
        return binding.root
    }
}