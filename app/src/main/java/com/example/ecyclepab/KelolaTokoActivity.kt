package com.example.ecyclepab

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecyclepab.adapters.ProfileProductAdapter
import com.example.ecyclepab.models.Product

class KelolaTokoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProfileProductAdapter
    private lateinit var productList: ArrayList<Product>

    // Penjual Info
    private lateinit var sellerName: TextView
    private lateinit var sellerLocation: TextView
    private lateinit var sellerRating: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kelola_toko)

        // Tombol kembali
        val btnBack: ImageButton = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        // Inisialisasi TextViews untuk informasi penjual
        sellerName = findViewById(R.id.sellerName)
        sellerLocation = findViewById(R.id.sellerLocation)
        sellerRating = findViewById(R.id.sellerRating)

        // Set informasi penjual
        sellerName.text = "Rachel"
        sellerLocation.text = "Tangerang Selatan"
        sellerRating.text = "4.6"

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // Menggunakan Grid, 2 kolom

        // Sample data untuk produk
        productList = ArrayList()
        // Pastikan imageResId adalah Int dan price, rating dimasukkan dengan benar
        productList.add(Product("Keyboard", "Rp. 500.000", "4.6", R.drawable.keyboard_image))
        productList.add(Product("Asus TUF", "Rp. 14.000.000", "4.5", R.drawable.asus_tuf_image))
        productList.add(Product("SSD NVME", "Rp. 450.000", "4.6", R.drawable.item3))
        productList.add(Product("Apple Macbook", "Rp. 16.000.000", "4.6", R.drawable.item4))
        productList.add(Product("Mechanical Keyboard", "Rp. 650.000", "4.5", R.drawable.orang))
        productList.add(Product("Asus ROG", "Rp. 25.000.000", "4.6", R.drawable.orang))

        // Set Adapter
        productAdapter = ProfileProductAdapter(productList)
        recyclerView.adapter = productAdapter
    }
}