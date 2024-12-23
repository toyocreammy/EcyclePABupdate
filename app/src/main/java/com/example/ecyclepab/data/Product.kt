package com.example.ecyclepab.data
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val name: String,
    val price: String,
    val category: String,
    val images: List<Int>,
    val details: Map<String, String> = emptyMap(), // Details produk
    val description: String, // Deskripsi produk
    val discussions: List<Discussion> = emptyList(), // Diskusi produk
    var quantity: Int = 1,
    var isSelected: Boolean = false
) : Parcelable

