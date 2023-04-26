package com.example.gym_system.models.detallespredio.cedula

data class Data(
    val cMotivo: String,
    val dFondo: Int,
    val dFrente: Int,
    val dSuperficieConstruccion: Int,
    val dSuperficieTerreno: Int,
    val dValorCatastral: Double,
    val dtFechaCedula: String,
    val iFolioCedula: String,
    val iIDCedula: Int,
    val lVigente: Boolean
)