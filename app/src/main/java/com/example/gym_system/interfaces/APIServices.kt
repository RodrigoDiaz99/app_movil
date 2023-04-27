package com.example.gym_system.interfaces

import android.util.Log

import com.example.gym_system.models.productos.getProducts
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Retrofit
import retrofit2.http.POST
import retrofit2.http.Query

interface APIServices {
    //obtencion de los datos notaria digital y ciudadano
    @GET("getProductos")

    fun getProductos(
    ): Call<getProducts>

    //obtencion del seguimiento de las solicitudes obtenidad previamente
    /*@GET("getSeguimientoRPP/{iIDSolicitud}/{iIDAnioFiscal}/{iIDDetalle}")


    fun getSeguimeinto(
        @Path("iIDSolicitud") iIDSolicitud: Int,
        @Path("iIDAnioFiscal") iIDAnioFiscal: Int,
        @Path("iIDDetalle") iIDDetalle: Int
    ): Call<SeguimientoClass>*/


    //obtencion de los datos de avaluo


    /*@POST("gridTramitesRPPMNC")
    fun getSolicitudComercio(
        @Query("var_s") iIDSolicitud: Int,
        @Query("var_a") iIDAnioFiscal: Int
    ): Call<SeguimientoComercioClass>*/


}