package com.example.app_insejupy.models.seguimiento.catastro

data class SeguimientoCatastroClass(
    val cHoraSolicitud: String,
    val cMensaje: String,
    val dtFechaSolicitud: String,
    val iIDAnioFiscal: String,
    val iIDSolicitud: String,
    val iTotalTramites: Int,
    val lSuccess: Boolean,
    val lstDetalles: List<LstDetalle>
)