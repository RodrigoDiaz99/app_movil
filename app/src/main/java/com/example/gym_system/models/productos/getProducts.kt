package com.example.gym_system.models.productos

data class getProducts(
    val `data`: List<Data>,
    val iError: Int,
    val lSuccess: Boolean
)