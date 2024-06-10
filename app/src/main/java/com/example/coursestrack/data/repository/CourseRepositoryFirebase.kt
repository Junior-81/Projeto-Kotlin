package com.example.coursestrack.data.repository

import android.util.Log
import com.example.coursestrack.data.model.Course
import com.example.coursestrack.data.model.Institution
import com.example.coursestrack.data.model.Matter
import com.example.coursestrack.util.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CourseRepositoryFirebase(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : CourseRepository {
    override fun createCourse(
        course: Course,
        institution: Institution,
        matter: Matter,
        result: (UiState<Course>) -> Unit
    ) {
        val document = firestore.collection("courses").document()
        val institutionRef = institution.id?.let {
            firestore.collection("institutions").document(it)
        }
        val matterRef = matter.id?.let {
            firestore.collection("matters").document(it)
        }
        val newCourse = course.copy(
            id = document.id,
            userId = auth.uid!!,
            progress = 0,
            institutionRef = institutionRef,
            matterRef = matterRef,
            matterName = matter.name,
            institutionName = institution.name
        )

        document.set(newCourse).addOnSuccessListener {
            result.invoke(UiState.Success(newCourse))
        }.addOnFailureListener { e ->
            result.invoke(UiState.Failure("Houve um erro na crição do curso, tente novamente mais tarde"))
            Log.d("my-app-erros", "firestore error to course course: $e")
        }
    }

    override fun getAllCourses(userId: String, result: (UiState<List<Course>>) -> Unit) {
        firestore.collection("courses")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val courses = mutableListOf<Course>()
                for (document in querySnapshot.documents) {
                    val course = document.toObject(Course::class.java)
                    if (course != null) {
                        courses.add(course)
                    }
                }
                result.invoke(UiState.Success(courses))
            }
            .addOnFailureListener { e ->
                result.invoke(UiState.Failure("Houve um erro ao buscar cursos"))
                Log.d("my-app-erros", "firestore error to get courses: $e ")
            }
    }

    override fun updateCourseProgress(
        course: Course,
        additionalProgress: Long,
        result: (UiState<String>) -> Unit
    ) {
        val courseRef = firestore.collection("courses").document(course.id!!)

        val currentProgress = course.progress?.toLong() ?: 0L
        val newProgress = currentProgress + additionalProgress

        if (course.duration != null && newProgress > course.duration.toLong()) {
            result.invoke(UiState.Failure("O progresso não pode ser maior que a duração do curso"))
            return
        }

        courseRef.update("progress", newProgress)
            .addOnSuccessListener {
                result.invoke(UiState.Success("Progresso atualizado"))
            }
            .addOnFailureListener { e ->
                result.invoke(UiState.Failure("Houve um erro na atualização do progresso, tente novamente mais tarde"))
                Log.d("my-app-erros", "firestore error to update course progress: $e")
            }
    }
}