package com.example.mediatore.models

import java.time.LocalDate

data class DependentResponse(val nomeDependente: String, val cpfDependente: String, val dataNascimento: String, val fkResponsavel: ResponsibleResponse)

