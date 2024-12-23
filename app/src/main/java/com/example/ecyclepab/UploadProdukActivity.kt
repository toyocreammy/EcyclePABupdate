package com.example.ecyclepab

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class UploadProdukActivity : AppCompatActivity() {

    private var selectedImageUri: Uri? = null // Uri untuk menyimpan path gambar yang dipilih

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_produk)

        // Reference to input fields
        val etNamaProduk = findViewById<EditText>(R.id.etNamaProduk)
        val spinnerKategori = findViewById<Spinner>(R.id.spinnerKategori)
        val etHarga = findViewById<EditText>(R.id.etHarga)
        val etBerat = findViewById<EditText>(R.id.etBerat)
        val etStok = findViewById<EditText>(R.id.etStok)
        val etDeskripsi = findViewById<EditText>(R.id.etDeskripsi)
        val etGaransi = findViewById<EditText>(R.id.etGaransi)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val btnAddPhoto = findViewById<ImageView>(R.id.btnAddPhoto)
        val ivBackButton = findViewById<ImageView>(R.id.ivBackButton) // Tambahkan ini untuk tombol kembali

        // Set up kategori spinner
        val kategoriArray = resources.getStringArray(R.array.kategori_array)
        val kategoriAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, kategoriArray)
        kategoriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerKategori.adapter = kategoriAdapter

        // Back button logic
        ivBackButton.setOnClickListener {
            // Kembali ke HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Button to add photo (open gallery)
        btnAddPhoto.setOnClickListener {
            openGallery()
        }

        // Submit button logic
        btnSubmit.setOnClickListener {
            val namaProduk = etNamaProduk.text.toString().trim()
            val kategori = spinnerKategori.selectedItem.toString()
            val harga = etHarga.text.toString().trim()
            val berat = etBerat.text.toString().trim()
            val stok = etStok.text.toString().trim()
            val garansi = etGaransi.text.toString().trim()
            val deskripsi = etDeskripsi.text.toString().trim()

            if (namaProduk.isEmpty() || harga.isEmpty() || berat.isEmpty() || stok.isEmpty() || deskripsi.isEmpty() || garansi.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            } else if (selectedImageUri == null) {
                Toast.makeText(this, "Silakan pilih foto produk", Toast.LENGTH_SHORT).show()
            } else {
                uploadProductToFirebase(namaProduk, kategori, harga, berat, stok, garansi, deskripsi)
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 100) // Request code 100 untuk identifikasi
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data // Dapatkan URI gambar
            val btnAddPhoto = findViewById<ImageView>(R.id.btnAddPhoto)
            btnAddPhoto.setImageURI(selectedImageUri) // Tampilkan gambar yang dipilih di ImageView
        }
    }

    private fun uploadProductToFirebase(
        namaProduk: String,
        kategori: String,
        harga: String,
        berat: String,
        stok: String,
        garansi: String,
        deskripsi: String
    ) {
        val storageReference = FirebaseStorage.getInstance().reference
        val fileName = "products/${UUID.randomUUID()}.jpg" // Nama unik untuk file
        val fileRef = storageReference.child(fileName)

        // Upload gambar ke Firebase Storage
        selectedImageUri?.let { uri ->
            fileRef.putFile(uri)
                .addOnSuccessListener {
                    fileRef.downloadUrl.addOnSuccessListener { imageUrl ->
                        saveProductToFirestore(
                            namaProduk,
                            kategori,
                            harga,
                            berat,
                            stok,
                            garansi,
                            deskripsi,
                            imageUrl.toString()
                        )
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal mengupload gambar: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun saveProductToFirestore(
        namaProduk: String,
        kategori: String,
        harga: String,
        berat: String,
        stok: String,
        garansi: String,
        deskripsi: String,
        imageUrl: String
    ) {
        val db = FirebaseFirestore.getInstance()
        val product = hashMapOf(
            "namaProduk" to namaProduk,
            "kategori" to kategori,
            "harga" to harga,
            "berat" to berat,
            "stok" to stok,
            "garansi" to garansi,
            "deskripsi" to deskripsi,
            "imageUrl" to imageUrl,
            "createdAt" to System.currentTimeMillis()
        )

        db.collection("products")
            .add(product)
            .addOnSuccessListener {
                Toast.makeText(this, "Produk berhasil diupload!", Toast.LENGTH_SHORT).show()
                finish() // Kembali ke HomeActivity
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal menyimpan produk: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
