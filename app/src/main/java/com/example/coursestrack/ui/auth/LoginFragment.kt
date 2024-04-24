package com.example.coursestrack.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coursestrack.R
import com.example.coursestrack.databinding.FragmentLoginBinding
import com.example.coursestrack.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment() {
    val TAG: String = "LoginFragment"
    lateinit var binding: FragmentLoginBinding
    val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()

        binding.createAccountLabel.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.submitLoginBtn.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            if (validate(email, password)){
                viewModel.login(email, password)
            }
        }
    }

    private fun observer() {
        viewModel.login.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Loading -> {
                    binding.submitLoginBtn.setText("")
                    binding.btnProgress.show()
                }

                is UiState.Failure -> {
                    binding.btnProgress.hide()
                    binding.submitLoginBtn.setText("Entrar")
                    Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                }

                is UiState.Success -> {
                    binding.btnProgress.hide()
                    binding.submitLoginBtn.setText("Entrar")
                    Toast.makeText(context, state.data, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun validate(email: String, password: String): Boolean {
        var isValid = true
        if (email.isNullOrEmpty()){
            Toast.makeText(context, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT)
                .show()
            binding.emailLayout.error = "Informe um email"
            isValid = false
        }

        if (password.isNullOrEmpty()){
            Toast.makeText(context, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT)
                .show()
            binding.passwordLayout.error = "Informe uma senha"
            isValid = false
        }
        return isValid
    }
}