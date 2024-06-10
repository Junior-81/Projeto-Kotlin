package com.example.coursestrack.data.model

import com.google.firebase.firestore.DocumentReference

data class Course(
    val id: String? = null,
    val name: String = "",
    val userId: String = "",
    val durationType: String = "",
    val duration: Long? = null,
    val progress: Long? = null,
    val institutionName: String? = null,
    val matterName: String? = null,
    val institutionRef: DocumentReference? = null,
    val matterRef: DocumentReference? = null
)
