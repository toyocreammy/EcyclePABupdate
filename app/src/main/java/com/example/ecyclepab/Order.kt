package com.example.ecyclepab

data class Order(
    val productName: String,
    val productImageResId: Int, // ID resource gambar dari drawable
    val price: Double,
    val status: String // Status produk (Dikirim atau Selesai)
)
