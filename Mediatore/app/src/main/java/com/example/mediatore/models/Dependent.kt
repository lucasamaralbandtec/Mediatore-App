package com.example.mediatore.models

import java.time.LocalDate

data class Dependent(val nomeDependente: String, val cpfDependente: String, val dataNascimento: LocalDate, val fkResponsavel: ResponsibleResponse)

