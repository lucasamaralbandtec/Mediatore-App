package com.example.mediatore.conections

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConectionAppointment {

    fun createConectionAppointment(): ApiAppointment {

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiAppointment::class.java)
    }
}