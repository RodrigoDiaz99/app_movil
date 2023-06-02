package com.example.gym_system.models.productos



data class Data(
    val cCodeBar: String,
    val cNombreProduct: String,
    val iIDProducto: Int,
    var quantity: Int,
    val price: Double
)
