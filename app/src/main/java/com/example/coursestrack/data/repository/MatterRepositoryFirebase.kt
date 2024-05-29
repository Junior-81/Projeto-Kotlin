package com.example.coursestrack.data.repository

import android.util.Log
import com.example.coursestrack.data.model.Matter
import com.example.coursestrack.util.UiState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class MatterRepositoryFirebase(
    val firestore: FirebaseFirestore,
    val authRepository: AuthRepository
) : MatterRepository {
    private var userId: String = ""

    init {
        getUserId()
    }

    private fun getUserId() {
        authRepository.getSession { id ->
            userId = id.toString()
        }
    }

    override suspend fun getAllMattersByUser(result: (UiState<List<Matter>>) -> Unit) {

        try {
            val matters = mutableListOf<Matter>()
            val querySnapshot: QuerySnapshot = firestore.collection("matters")
                .whereEqualTo("userId", userId)
                .get()
                .await()

            for (document in querySnapshot.documents) {
                document.toObject(Matter::class.java)?.let {
                    matters.add(it)
                }
            }

            result.invoke(UiState.Success(matters))
        } catch (e: Exception) {
            result.invoke(UiState.Failure("Houve um erro na consulta das matérias"))
            Log.d("my-app-erros", "firestore error: $e")
        }
    }

    override suspend fun createMatter(
        name: String,
        result: (UiState<Matter>) -> Unit
    ) {
        try {
            val newMatter = Matter(
                name = name,
                userId = userId
            )

            firestore.collection("matters").add(newMatter).await()
            result.invoke(UiState.Success(newMatter))
        } catch (e: Exception) {
            result.invoke(UiState.Failure("Houve um erro na criação da matéria"))
            Log.d("my-app-erros", "firestore error: $e")
        }
    }
}