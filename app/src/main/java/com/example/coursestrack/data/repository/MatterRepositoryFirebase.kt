package com.example.coursestrack.data.repository

import android.util.Log
import com.example.coursestrack.data.model.Matter
import com.example.coursestrack.util.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MatterRepositoryFirebase(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : MatterRepository {
    private var userId: String = auth.uid!!

    override fun getAllMattersByUser(result: (UiState<List<Matter>>) -> Unit) {
        firestore.collection("matters")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val matters = mutableListOf<Matter>()
                for (document in querySnapshot.documents) {
                    val matter = document.toObject(Matter::class.java)
                    if (matter != null) {
                        matters.add(matter)
                    }
                }
                result.invoke(UiState.Success(matters))
            }
            .addOnFailureListener { e ->
                result.invoke(UiState.Failure("Houve um erro na consulta das matérias"))
                Log.d("my-app-erros", "firestore error to get matters: $e")
            }
    }

    override fun createMatter(
        name: String,
        result: (UiState<Matter>) -> Unit
    ) {
        val document = firestore.collection("matters").document()
        val newMatter = Matter(document.id, name, userId)

        document.set(newMatter)
            .addOnSuccessListener {
                result.invoke(UiState.Success(newMatter))
            }
            .addOnFailureListener { e ->
                result.invoke(UiState.Failure("Houve um erro na criação da matéria"))
                Log.d("my-app-erros", "firestore error to course matter: $e")
            }
    }
}