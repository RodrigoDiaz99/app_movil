package com.example.app_insejupy.models.seguimiento.comercio

data class LstDetalle(
    val cClave: String,
    val cEstatus: String,
    val cEtapa: String,
    val cOperacion: String,
    val cProceso: String,
    val cUsuario: String,
    val dtFecha: String,
    val dtFechaAlta: String,
    val iFolioCivil: String,
    val iIDAnioFiscal: String,
    val iIDDetalleSolicitud: String,
    val iIDEtapa: String,
    val iIDFolio: String,
    val iIDGestoriaPatente: String,
    val iIDOperacion: String,
    val iIDSolicitud: Int
)