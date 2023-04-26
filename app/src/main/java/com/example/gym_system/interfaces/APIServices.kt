package com.example.gym_system.interfaces

import android.util.Log
import com.example.app_insejupy.models.predio.resultado.PredioClass
import com.example.app_insejupy.models.seguimiento.catastro.SeguimientoCatastroClass
import com.example.app_insejupy.models.seguimiento.catastro.seguimiento.CatastroSeguimientoClass
import com.example.app_insejupy.models.seguimiento.comercio.SeguimientoComercioClass
import com.example.app_insejupy.models.seguimiento.comercio.seguimiento.LineaTiempoComercioClass
import com.example.app_insejupy.models.seguimiento.rpp.Seguimiento
import com.example.app_insejupy.models.seguimiento.rpp.seguimiento.SeguimientoClass
import com.example.pruebadrawer.models.QR.Catastro.QrCatastroClass
import com.example.pruebadrawer.models.detallespredio.InscripcionClass
import com.example.pruebadrawer.models.detallespredio.cedula.CedulaClass
import com.example.pruebadrawer.models.detallespredio.nomenclatura.NomenclaturaClass
import com.example.pruebadrawer.models.QR.RPP.QrRPPClass
import com.example.pruebadrawer.models.seguimiento.avaluo.SeguimientoAvaluoClass
import com.example.pruebadrawer.models.seguimiento.avaluo.seguimiento.AvaluoSeguimientoClass
import com.example.pruebadrawer.models.tomo.resultado.TomoClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Retrofit
import retrofit2.http.POST
import retrofit2.http.Query

interface APIServices {
    //obtencion de los datos notaria digital y ciudadano
    @GET("getSolicitudRPP/{iIDSolicitud}/{iIDAnioFiscal}")

    fun getSolicitud(
        @Path("iIDSolicitud") iIDSolicitud: Int,
        @Path("iIDAnioFiscal") iIDAnioFiscal: Int
    ): Call<Seguimiento>

    //obtencion del seguimiento de las solicitudes obtenidad previamente
    @GET("getSeguimientoRPP/{iIDSolicitud}/{iIDAnioFiscal}/{iIDDetalle}")


    fun getSeguimeinto(
        @Path("iIDSolicitud") iIDSolicitud: Int,
        @Path("iIDAnioFiscal") iIDAnioFiscal: Int,
        @Path("iIDDetalle") iIDDetalle: Int
    ): Call<SeguimientoClass>

    //obtencion de los datos de catastro
    @GET("getSolicitudCatastro/{iIDSolicitud}/{iIDAnioFiscal}")
    fun getSolicitudCatastro(
        @Path("iIDSolicitud") iIDSolicitud: Int,
        @Path("iIDAnioFiscal") iIDAnioFiscal: Int
    ): Call<SeguimientoCatastroClass>

    //obtencion de los datos de seguimiento de catastro
    @GET("getSeguimientoCatastro/{iIDSolicitud}/{iIDAnioFiscal}/{iIDDetalleSolicitud}")
    fun getSeguimientoCatastro(
        @Path("iIDSolicitud") iIDSolicitud: Int,
        @Path("iIDAnioFiscal") iIDAnioFiscal: Int,
        @Path("iIDDetalleSolicitud") iIDDetalle: Int
    ): Call<CatastroSeguimientoClass>

    //obtencion de los datos de avaluo
    @GET("getSolicitudAvaluo/{iIDSolicitud}/{iIDAnioFiscal}")
    fun getSolicitudAvaluo(
        @Path("iIDSolicitud") iIDSolicitud: Int,
        @Path("iIDAnioFiscal") iIDAnioFiscal: Int
    ): Call<SeguimientoAvaluoClass>

    @GET("getSeguimientoAvaluo/{iIDSolicitud}/{iIDAnioFiscal}")
    fun getSeguimientoAvaluo(
        @Path("iIDSolicitud") iIDSolicitud: Int,
        @Path("iIDAnioFiscal") iIDAnioFiscal: Int
    ): Call<AvaluoSeguimientoClass>

    @POST("gridTramitesRPPMNC")
    fun getSolicitudComercio(
        @Query("var_s") iIDSolicitud: Int,
        @Query("var_a") iIDAnioFiscal: Int
    ): Call<SeguimientoComercioClass>

    @POST("getSeguimientoRPPMNC")
    fun getSeguimientoComercio(
        @Query("iIDSolicitud") iIDSolicitud: Int,
        @Query("iIDAnioFiscal") iIDAnioFiscal: Int,
        @Query("iIDDetalle") iIDDetalle: Int
    ): Call<LineaTiempoComercioClass>

    @GET("getPredio/{iIDPredio}/{cCalle}/{cNumero}/{cManzana}/{cTablaje}/{cSeccion}/{cMunicipio}/{opcion}")
    fun getPredio(
        @Path("iIDPredio") iIDPredio: Int,
        @Path("cCalle") cCalle: Int,
        @Path("cNumero") cNumero: Int,
        @Path("cManzana") cManzana: Int,
        @Path("cTablaje") cTablaje: Int,
        @Path("cSeccion") cSeccion: Int,
        @Path("cMunicipio") cMunicipio: String,
        @Path("opcion") opcion: Int
    ): Call<PredioClass>

    @GET("getTomo/{iTipoPredio}/{iVolumenPredio}/{cLetraLibro}/{cFolioLibro}/{iLibro}/{cTomo}")
    fun getTomo(
        @Path("iTipoPredio") iTipoPredio: Int,
        @Path("iVolumenPredio") iVolumenPredio: Int,
        @Path("cLetraLibro") cLetraLibro: String,
        @Path("cFolioLibro") cFolioLibro: Int,
        @Path("iLibro") iLibro: Int,
        @Path("cTomo") cTomo: Int
    ): Call<TomoClass>

    @GET("getInscripcionPredio/{iIDPredio}")
    fun getInscripcionPredio(
        @Path("iIDPredio") iIDPredio: Int
    ): Call<InscripcionClass>

    @GET("getNomenclaturaPredio/{iIDPredio}")
    fun getNomenclaturaPredio(
        @Path("iIDPredio") iIDPredio: Int
    ): Call<NomenclaturaClass>

    @GET("getCedulaPredio/{iIDPredio}/{cMunicipio}")
    fun getCedulaPredio(
        @Path("iIDPredio") iIDPredio: Int,
        @Path("cMunicipio") cMunicipio: Int
    ): Call<CedulaClass>

    @GET("getBoletaGeneral/{datax}")
    fun getBoletaGeneral(
        @Path("datax") last: String,

        ): Call<QrRPPClass>

    @GET("getHojaCatastro/{cedula}/{idcatastro}")
    fun getHojaCatastro(
        @Path("cedula") cedula: String,
        @Path("idcatastro") idCatastro: String

    ): Call<QrCatastroClass> {
        val url = "getHojaCatastro/$cedula/$idCatastro"
        Log.d("DEBUG", "URL de la solicitud: $url")
        return getHojaCatastro(
            cedula, idCatastro
        )
    }

}