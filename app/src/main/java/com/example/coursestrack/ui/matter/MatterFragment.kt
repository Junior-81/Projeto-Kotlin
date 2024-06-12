package com.example.coursestrack.ui.matter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.coursestrack.adapters.MatterAdapter
import com.example.coursestrack.databinding.FragmentMatterBinding
import com.example.coursestrack.ui.dialogs.CreationDialog
import com.example.coursestrack.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatterFragment : Fragment() {
    private lateinit var binding: FragmentMatterBinding
    private val viewModel: MatterViewModel by viewModels()
    private val matterAdapter = MatterAdapter(
        onEditButtonClicked = { matter ->
        },
        onDeleteButtonClicked = { matter ->
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createMatterBtn.setOnClickListener {
            CreationDialog("Adicionar matéria", "matéria") { matterName ->
                viewModel.createMatter(matterName)
            }.show(
                childFragmentManager,
                "createMatterDialog"
            )
        }

        binding.recyclerview.adapter = matterAdapter

        viewModel.getAllMatters()

        observer()
    }

    private fun observer() {
        viewModel.matters.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {

                }

                is UiState.Failure -> {
                    // Trate a falha conforme necessário

                }

                is UiState.Success -> {
                    matterAdapter.setMattersList(state.data)
                }
            }
        }
    }
}
