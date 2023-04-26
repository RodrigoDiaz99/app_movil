package com.example.app_insejupy.models.seguimiento.rpp

data class Data(
    val detalles: List<Detalle>,
    val iIDAnioFiscal: String,
    val iIDSolicitud: String
)