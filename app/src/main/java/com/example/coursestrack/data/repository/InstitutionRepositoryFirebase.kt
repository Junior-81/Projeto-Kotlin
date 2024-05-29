package com.example.coursestrack.data.repository

import android.util.Log
import com.example.coursestrack.data.model.Institution
import com.example.coursestrack.util.UiState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

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

    override suspend fun getAllInstitutionsByUser(result: (UiState<List<Institution>>) -> Unit) {
        try {
            val institutions = mutableListOf<Institution>()
            val querySnapshot: QuerySnapshot = firestore.collection("institutions")
                .whereEqualTo("userId", userId)
                .get()
                .await()

            for (document in querySnapshot.documents) {
                document.toObject(Institution::class.java)?.let {
                    institutions.add(it)
                }
            }

            result.invoke(UiState.Success(institutions))
        } catch (e: Exception) {
            result.invoke(UiState.Failure("Houve um erro na consulta das instituições"))
            Log.d("my-app-erros", "firestore error: $e")
        }
    }

    override suspend fun createInstitution(
        name: String,
        result: (UiState<Institution>) -> Unit
    ) {
        try {
            val newInstitution = Institution(
                name = name,
                userId = userId
            )

            firestore.collection("institutions").add(newInstitution).await()
            result.invoke(UiState.Success(newInstitution))
        } catch (e: Exception) {
            result.invoke(UiState.Failure("Houve um erro na criação da instituição"))
            Log.d("my-app-erros", "firestore error: $e")
        }
    }
}