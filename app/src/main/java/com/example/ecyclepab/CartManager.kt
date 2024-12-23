package com.example.ecyclepab

import android.content.Context
import com.example.ecyclepab.data.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartManager(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("CartPreferences", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun addToCart(product: Product) {
        val cartItems = getCartItems().toMutableList()
        val existingProduct = cartItems.find { it.name == product.name }
        if (existingProduct != null) {
            // Jika produk sudah ada, tambahkan kuantitas
            existingProduct.quantity += product.quantity
        } else {
            // Jika produk belum ada, tambahkan ke daftar
            cartItems.add(product)
        }
        saveCartItems(cartItems)
    }

    fun getCartItems(): List<Product> {
        val json = sharedPreferences.getString("cart_items", null)
        return if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<List<Product>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    private fun saveCartItems(cartItems: List<Product>) {
        val json = gson.toJson(cartItems)
        sharedPreferences.edit().putString("cart_items", json).apply()
    }
}
