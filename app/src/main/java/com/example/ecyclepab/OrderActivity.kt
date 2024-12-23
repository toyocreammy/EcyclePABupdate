package com.example.ecyclepab

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrderActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var orderHistoryAdapter: OrderHistoryAdapter
    private lateinit var orderList: List<Order>
    private lateinit var filteredList: List<Order>
    private lateinit var btnAll: Button
    private lateinit var btnInProgress: Button
    private lateinit var btnCompleted: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order) // Menggunakan layout baru

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // Grid dengan 2 kolom

        // Inisialisasi tombol filter
        btnAll = findViewById(R.id.btnAll)
        btnInProgress = findViewById(R.id.btnInProgress)
        btnCompleted = findViewById(R.id.btnCompleted)

        // Simulasi data produk (8 produk dengan status)
        orderList = listOf(
            Order("TMA-2 HD Wireless", R.drawable.headphone, 1500000.0, "Dikirim"),
            Order("Logitech G304", R.drawable.mouse1, 500000.0, "Dikirim"),
            Order("SSD NVME M.2", R.drawable.ssd, 1000000.0, "Selesai"),
            Order("Keyboard", R.drawable.keyboard, 1200000.0, "Selesai"),
            Order("TMA-2 HD Wireless", R.drawable.headphone, 1500000.0, "Dikirim"),
            Order("Logitech G304", R.drawable.mouse1, 500000.0, "Selesai"),
            Order("SSD NVME M.2", R.drawable.ssd, 1000000.0, "Dikirim"),
            Order("Keyboard", R.drawable.keyboard, 1200000.0, "Selesai")
        )

        // Default: tampilkan semua produk
        filteredList = orderList
        orderHistoryAdapter = OrderHistoryAdapter(filteredList)
        recyclerView.adapter = orderHistoryAdapter

        // Filter tombol
        btnAll.setOnClickListener {
            filteredList = orderList // Semua produk
            orderHistoryAdapter.updateData(filteredList)
        }

        btnInProgress.setOnClickListener {
            filteredList = orderList.filter { it.status == "Dikirim" } // Produk "Dikirim"
            orderHistoryAdapter.updateData(filteredList)
        }

        btnCompleted.setOnClickListener {
            filteredList = orderList.filter { it.status == "Selesai" } // Produk "Selesai"
            orderHistoryAdapter.updateData(filteredList)
        }
    }
}