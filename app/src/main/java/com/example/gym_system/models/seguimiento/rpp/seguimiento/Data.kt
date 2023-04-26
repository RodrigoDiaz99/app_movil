package com.example.app_insejupy.models.seguimiento.rpp.seguimiento

data class Data(
    val iIDAnioFiscal: String,
    val iIDSolicitud: String,
    val lstSeguimiento: List<LstSeguimiento>
)