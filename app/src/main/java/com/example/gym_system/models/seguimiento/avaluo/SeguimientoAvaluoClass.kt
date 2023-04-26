package com.example.gym_system.models.seguimiento.avaluo

data class SeguimientoAvaluoClass(
    val cEtapa: String,
    val cFechaSeguimiento: String,
    val cEstatus: String,
    val iIDAnioFiscal: String,
    val iIDSolicitud: String,
    val lSuccess: Boolean,
    val iError: Int
)