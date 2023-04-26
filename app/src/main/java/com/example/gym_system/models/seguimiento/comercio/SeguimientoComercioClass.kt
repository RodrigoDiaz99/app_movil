package com.example.app_insejupy.models.seguimiento.comercio

data class SeguimientoComercioClass(
    val cHoraSolicitud: String,
    val cMensaje: String,
    val dtFechaSolicitud: String,
    val iIDAnioFiscal: Any,
    val iIDSolicitud: Any,
    val iTotalTramites: Int,
    val lSuccess: Boolean,
    val iError: Int,
    val lstDetalles: List<LstDetalle>
)