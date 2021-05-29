package com.example.mediatore.conections

import com.example.mediatore.models.Dependent
import com.example.mediatore.models.DependentResponse
import com.example.mediatore.models.Responsible
import com.example.mediatore.models.ResponsibleResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiUsers
{
    @POST("/usuarios/login")
    fun login(@Body user: Responsible): Call<ResponsibleResponse>

    @POST("/usuarios")
    fun createUser(@Body newUser: Responsible): Call<Responsible>

    @GET("/usuarios/dependente/{idResponsavel}")
    fun getDependents(@Path("idResponsavel") idResponsible: Int): Call<List<DependentResponse>>

    @POST("/usuarios/dependente")
    fun createDependent(@Body newDependent: Dependent): Call<Dependent>

    @GET("/usuarios/{id}")
    fun getUser(@Path("id") idResponsible: Int): Call<ResponsibleResponse>

}