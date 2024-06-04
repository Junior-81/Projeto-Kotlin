package com.example.coursestrack.data.repository

import android.util.Log
import com.example.coursestrack.data.model.Matter
import com.example.coursestrack.util.UiState
import com.google.firebase.firestore.FirebaseFirestore

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

    override fun getAllMattersByUser(result: (UiState<List<Matter>>) -> Unit) {
        firestore.collection("matters")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val matters = mutableListOf<Matter>()
                for (document in querySnapshot.documents) {
                    val matter = document.toObject(Matter::class.java)?.copy(id = document.id)
                    if (matter != null) {
                        matters.add(matter)
                    }
                }
                result.invoke(UiState.Success(matters))
            }
            .addOnFailureListener { e ->
                result.invoke(UiState.Failure("Houve um erro na consulta das matérias"))
                Log.d("my-app-erros", "firestore error: $e")
            }
    }

    override fun createMatter(
        name: String,
        result: (UiState<Matter>) -> Unit
    ) {
        val newMatter = Matter(
            name = name,
            userId = userId
        )

        firestore.collection("matters")
            .add(newMatter)
            .addOnSuccessListener {
                result.invoke(UiState.Success(newMatter))
            }
            .addOnFailureListener { e ->
                result.invoke(UiState.Failure("Houve um erro na criação da matéria"))
                Log.d("my-app-erros", "firestore error: $e")
            }
    }
}