package com.example.gymapp.global

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    var quantity: Int = 1
)