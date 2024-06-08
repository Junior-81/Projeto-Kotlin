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
    private var userId: String = auth.uid!!

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
            userId = userId,
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
            Log.d("my-app-erros", "firestore error to create course: $e")
        }
    }

    override fun getAllCourses(result: (UiState<List<Course>>) -> Unit) {
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
}