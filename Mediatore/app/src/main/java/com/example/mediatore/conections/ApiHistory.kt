package com.example.mediatore.conections

import com.example.mediatore.models.History
import retrofit2.Call
import retrofit2.http.*

interface ApiHistory
{
    @POST("/historicos/{idUsuario}")
    fun createHistoryById(@Path("idUsuario") idUsuario: Int): Call<Void>

    @GET("historicos/usuario/{idUsuario}")
    fun getHistoryById(@Path("idUsuario") idUsuario: Int): Call<List<History>>
}