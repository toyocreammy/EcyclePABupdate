package com.example.ecyclepab

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecyclepab.adapter.ProductAdapter
import com.example.ecyclepab.data.Product
import com.example.ecyclepab.ProductDetailActivity

class ProdukCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produk_category)

        // Ambil data kategori dari Intent
        val category = intent.getStringExtra("selected_category") ?: "Kategori"
        val products = intent.getParcelableArrayListExtra<Product>("product_list") ?: arrayListOf()

        // Ubah judul header sesuai kategori
        val headerTitle = findViewById<TextView>(R.id.header_title)
        headerTitle.text = category.uppercase() // Menampilkan kategori dalam huruf kapital

        // Setup RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewProducts)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // 2 kolom
        recyclerView.adapter = ProductAdapter(products) { selectedProduct ->
            // Navigasi ke ProductDetailActivity saat produk diklik
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("selected_product", selectedProduct)
            startActivity(intent)
        }

        // Back Button
        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish() // Menutup Activity dan kembali ke sebelumnya
        }
    }
}
