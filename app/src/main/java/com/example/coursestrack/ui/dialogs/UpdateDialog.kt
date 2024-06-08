package com.example.coursestrack.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.coursestrack.data.model.Course
import com.example.coursestrack.databinding.DialogCreationBinding

class UpdateDialog(private val course: Course): DialogFragment() {
    lateinit var binding: DialogCreationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}