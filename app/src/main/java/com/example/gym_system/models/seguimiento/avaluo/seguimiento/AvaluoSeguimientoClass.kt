package com.example.gym_system.models.seguimiento.avaluo.seguimiento

data class AvaluoSeguimientoClass(
    val `data`: List<Data>,
    val iError: Int,
    val iIDAnioFiscal: String,
    val iIDSolicitud: String,
    val lSuccess: Boolean
)