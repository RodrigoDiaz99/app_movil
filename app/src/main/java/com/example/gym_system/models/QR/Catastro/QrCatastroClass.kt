package com.example.gym_system.models.QR.Catastro

data class QrCatastroClass(
    val cCadenaHash: String,
    val cCadenaOriginal: String,
    val cFechaFirma: String,
    val cOperacion: String,
    val cUrlDocumento: String,
    val iError: Int,
    val iIDAnioFiscal: Int,
    val iIDDetalle: Int,
    val iIDFolio: Int,
    val iIDSolicitud: Int,
    val lSuccess: Boolean
)