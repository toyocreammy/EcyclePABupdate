package com.example.ecyclepab.models

data class Product(
    val name: String,
    val price: String,  // Tetap String, jika Anda ingin memformatnya, lakukan di antarmuka.
    val rating: String, // Jika ingin menyimpan sebagai double, ubah jadi Double
    val imageResId: Int // Pastikan ini adalah Int
)