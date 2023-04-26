package com.example.app_insejupy.models.seguimiento.comercio.seguimiento

data class LineaTiempoComercioClass(
    val cMensaje: String,
    val lSuccess: Boolean,
    val lstSeguimiento: List<LstSeguimiento>
)