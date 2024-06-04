package com.example.coursestrack.data.repository

import android.util.Log
import com.example.coursestrack.data.model.Institution
import com.example.coursestrack.util.UiState
import com.google.firebase.firestore.FirebaseFirestore

class InstitutionRepositoryFirebase(
    val firestore: FirebaseFirestore,
    val authRepository: AuthRepository
) : InstitutionRepository {
    private var userId: String = ""

    init {
        getUserId()
    }

    private fun getUserId() {
        authRepository.getSession { id ->
            userId = id.toString()
        }
    }

    override fun getAllInstitutionsByUser(result: (UiState<List<Institution>>) -> Unit) {
        firestore.collection("institutions")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val institutions = mutableListOf<Institution>()
                for (document in querySnapshot.documents) {
                    val institution =
                        document.toObject(Institution::class.java)?.copy(id = document.id)
                    if (institution != null) {
                        institutions.add(institution)
                    }
                }
                result.invoke(UiState.Success(institutions))
            }
            .addOnFailureListener { e ->
                result.invoke(UiState.Failure("Houve um erro na consulta das matérias"))
                Log.d("my-app-erros", "firestore error: $e")
            }
    }

    override fun createInstitution(
        name: String,
        result: (UiState<Institution>) -> Unit
    ) {

        val newInstitution = Institution(
            name = name,
            userId = userId
        )

        firestore.collection("institutions")
            .add(newInstitution)
            .addOnSuccessListener {
                result.invoke(UiState.Success(newInstitution))
            }
            .addOnFailureListener { e ->
                result.invoke(UiState.Failure("Houve um erro na criação da matéria"))
                Log.d("my-app-erros", "firestore error: $e")
            }
    }
}