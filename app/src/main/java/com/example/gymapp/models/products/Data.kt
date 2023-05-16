package com.example.gym_system.models.productos

data class Data(
    val cCodeBar: String,
    val cNombreProduct: String,
    val iIDProducto: Int,
    val quantity: Int,
    val price: Double
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Data

        if (cCodeBar != other.cCodeBar) return false
        if (cNombreProduct != other.cNombreProduct) return false
        if (iIDProducto != other.iIDProducto) return false
        if (quantity != other.quantity) return false
        if (price != other.price) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cCodeBar.hashCode()
        result = 31 * result + cNombreProduct.hashCode()
        result = 31 * result + iIDProducto
        result = 31 * result + quantity
        result = 31 * result + price.hashCode()
        return result
    }
}
