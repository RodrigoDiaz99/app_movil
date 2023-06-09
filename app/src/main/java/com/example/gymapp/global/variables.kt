package com.example.gym_system.interfaces

import com.example.gymapp.global.APIServices
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


const val rutaancj = "https://spaciofems.plataformauniversal.com/api/"
lateinit var service: APIServices
private val client = OkHttpClient()
private val gson: Gson = GsonBuilder()
    .setLenient()
    .create()
val gson2 = Gson()
val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(rutaancj)
    .client(client)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()
const val message_success = "¡Se encontraron datos de esa solicitud!"
const val message_error = "¡No se encontraron datos de esa solicitud!"
const val message_api_error = "No se pudo establecer conexión"
