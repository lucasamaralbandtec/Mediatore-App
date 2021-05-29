package com.example.mediatore.models

data class Responsible
    (
    val rua:String,
    val bairro:String,
    val cidade:String,
    val pais:String,
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