package com.example.mediatore.models

abstract class User
{
    abstract val nome:String
    abstract val senha:String
    abstract val cep:String
    abstract val email:String
    abstract val telefone:String
    abstract val statusAtividade:Boolean
    abstract val cpf:String
    abstract val sexo:String
}