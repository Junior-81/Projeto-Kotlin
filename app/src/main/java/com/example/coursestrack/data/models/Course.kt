package com.example.coursestrack.data.models

data class Course(
    val nome: String,
    val categoria: String,
    val instituicao: String,
    val quantidadeTotal: Int,
    val quantidadeAssistidas: Int
)

object CursoMockData {
    fun getCursosMockados(): List<Course> {
        return listOf(
            Course(
                nome = "Kotlin para Iniciantes",
                categoria = "Programação",
                instituicao = "Udemy",
                quantidadeTotal = 30,
                quantidadeAssistidas = 15
            ),
            Course(
                nome = "Desenvolvimento Android",
                categoria = "Desenvolvimento Mobile",
                instituicao = "Coursera",
                quantidadeTotal = 50,
                quantidadeAssistidas = 25
            ),
            Course(
                nome = "Introdução ao Machine Learning",
                categoria = "Data Science",
                instituicao = "edX",
                quantidadeTotal = 40,
                quantidadeAssistidas = 10
            ),
            Course(
                nome = "Design de UI/UX",
                categoria = "Design",
                instituicao = "Alura",
                quantidadeTotal = 20,
                quantidadeAssistidas = 5
            ),
            Course(
                nome = "React para Iniciantes",
                categoria = "Desenvolvimento Web",
                instituicao = "Pluralsight",
                quantidadeTotal = 35,
                quantidadeAssistidas = 18
            )
        )
    }
}
