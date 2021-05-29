package com.example.mediatore.models

data class ResponsibleResponse
    (
    val rua:String,
    val bairro:String,
    val cidade:String,
    val pais:String,
    val idResponsavel:Int,
    override val nome: String,
    override val senha: String,
    override val cep: String,
    override val email: String,
    override val cpf: String,
    override val telefone: String,
    override val statusAtividade: Boolean,
    override val sexo: String
)
    : User()