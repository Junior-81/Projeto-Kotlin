package com.example.coursestrack.ui.matter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coursestrack.databinding.FragmentMatterBinding

class MatterFragment : Fragment() {
    private lateinit var binding: FragmentMatterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatterBinding.inflate(layoutInflater)
        return binding.root
    }
}