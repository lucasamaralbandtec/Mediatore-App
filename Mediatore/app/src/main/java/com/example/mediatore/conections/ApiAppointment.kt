package com.example.mediatore.conections

import com.example.mediatore.models.Appointment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiAppointment
{
    @GET("/consultas/usuario/{idUsuario}")
    fun getAppointmentById(@Path("idUsuario") idUsuario: Int): Call<List<Appointment>>

    @POST("/consultas/{idUsuario}/{idDependente}/{idClinica}")
    fun makeAnAppointment(@Path("idUsuario") idUsuario: Int, @Path("idDependente") idDependente: Int, @Path("idClinica") idClinica: Int): Call<Void>
}