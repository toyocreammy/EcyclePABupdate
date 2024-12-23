package com.example.ecyclepab

import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecyclepab.adapter.CartAdapter
import com.example.ecyclepab.CartManager
import com.example.ecyclepab.data.Product

class CartActivity : AppCompatActivity() {

    private lateinit var cartItems: MutableList<Product>
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_manager)

        val cartManager = CartManager(this)
        cartItems = cartManager.getCartItems().toMutableList()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCart)
        recyclerView.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartAdapter(cartItems)
        recyclerView.adapter = cartAdapter

        // Tombol Back
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // Checkbox Pilih Semua
        val checkboxSelectAll = findViewById<CheckBox>(R.id.checkboxSelectAll)
        checkboxSelectAll.setOnCheckedChangeListener { _, isChecked ->
            selectAllItems(isChecked)
        }
    }

    private fun selectAllItems(isChecked: Boolean) {
        // Perbarui status checkbox untuk semua item
        cartItems.forEach { it.isSelected = isChecked }
        cartAdapter.notifyDataSetChanged()
    }
}
