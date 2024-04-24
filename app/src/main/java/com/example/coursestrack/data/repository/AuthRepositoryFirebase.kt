package com.example.coursestrack.data.repository

import android.util.Log
import com.example.coursestrack.util.UiState
import com.google.firebase.auth.FirebaseAuth

class AuthRepositoryFirebase (val auth: FirebaseAuth): AuthRepository {
    val TAG: String = "AuthRepository"

    override fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Usuario logado com sucesso")
                    result.invoke(UiState.Success("Login realizado com sucesso"))
                }
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure("Falha na autenticação. Verifique email e senha"))
            }
    }
}
