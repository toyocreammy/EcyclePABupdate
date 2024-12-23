package com.example.ecyclepab

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        // Menghubungkan tombol kembali dari layout
        val btnBack: ImageButton = findViewById(R.id.btnBack)

        // Menambahkan listener untuk tombol kembali
        btnBack.setOnClickListener {
            finish()
        }

        // Menyimpan alamat
        val saveAddressButton: Button = findViewById(R.id.saveAddressButton)
        saveAddressButton.setOnClickListener {
            // Ambil data alamat dari EditText dan simpan sesuai kebutuhan
            Toast.makeText(this, "Address saved!", Toast.LENGTH_SHORT).show()
            finish() // Kembali ke aktivitas sebelumnya setelah alamat disimpan.
        }
    }
}