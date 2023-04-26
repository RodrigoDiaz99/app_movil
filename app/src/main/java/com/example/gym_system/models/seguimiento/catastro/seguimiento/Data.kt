package com.example.app_insejupy.models.seguimiento.catastro.seguimiento

data class Data(
    val iIDAnioFiscal: String,
    val iIDSolicitud: String,
    val lstSeguimiento: List<LstSeguimiento>
)