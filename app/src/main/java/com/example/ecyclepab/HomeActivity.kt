package com.example.ecyclepab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecyclepab.adapter.CategoryAdapter
import com.example.ecyclepab.adapter.ProductAdapter
import com.example.ecyclepab.data.Category
import com.example.ecyclepab.data.Discussion
import com.example.ecyclepab.data.Product
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var featuredProductAdapter: ProductAdapter
    private lateinit var laptopCollectionAdapter: ProductAdapter
    private lateinit var under20Adapter: ProductAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Inisialisasi Firebase
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // Setup RecyclerView for Categories
        val categoryRecyclerView = findViewById<RecyclerView>(R.id.categoryRecyclerView)
        categoryRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = CategoryAdapter(getCategories()) { category ->
            onCategoryClick(category)
        }
        categoryRecyclerView.adapter = categoryAdapter

        // Setup RecyclerView for Featured Products
        val featuredProductRecyclerView =
            findViewById<RecyclerView>(R.id.featuredProductRecyclerView)
        featuredProductRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        featuredProductAdapter = ProductAdapter(getFeaturedProducts()) { product ->
            onProductClick(product)
        }
        featuredProductRecyclerView.adapter = featuredProductAdapter

        // Setup RecyclerView for Laptop Collection
        val laptopCollectionRecyclerView =
            findViewById<RecyclerView>(R.id.laptopCollectionRecyclerView)
        laptopCollectionRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        laptopCollectionAdapter = ProductAdapter(getLaptopCollection()) { product ->
            onProductClick(product)
        }
        laptopCollectionRecyclerView.adapter = laptopCollectionAdapter

        // Ambil nama pengguna dari Firestore
        fetchUserName()

        // Setup RecyclerView for Under 20 Juta
        val under20RecyclerView = findViewById<RecyclerView>(R.id.under20RecyclerView)
        under20RecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        under20Adapter = ProductAdapter(getUnder20Products()) { product ->
            onProductClick(product)
        }
        under20RecyclerView.adapter = under20Adapter

        // Tambahkan logika untuk tombol upload
        val btnUploadProduct = findViewById<Button>(R.id.btnUploadProduct)
        btnUploadProduct.setOnClickListener {
            val intent = Intent(this, UploadProdukActivity::class.java)
            startActivity(intent)
        }

        // Tambahkan listener untuk ikon Cart
        val cartIcon = findViewById<ImageView>(R.id.cartIcon)
        cartIcon.setOnClickListener {
            navigateToCart()
        }

        // Inisialisasi Bottom Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Halaman Home sudah aktif
                    true
                }
                R.id.nav_explore -> {
                    val intent = Intent(this, ExploreActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_order -> {
                    val intent = Intent(this, OrderActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_account -> {
                    val intent = Intent(this, AccountActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun fetchUserName() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val userName = document.getString("name") ?: "User"
                        val userNameTextView = findViewById<TextView>(R.id.namauser)
                        userNameTextView.text = userName
                    } else {
                        Toast.makeText(this, "User not found in database", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        this,
                        "Failed to fetch user data: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    // Dummy Data for Categories
    private fun getCategories(): List<Category> {
        return listOf(
            Category("Keyboard", R.drawable.ic_keyboard),
            Category("Gadget", R.drawable.ic_gadget),
            Category("PC", R.drawable.ic_pc),
            Category("Laptop", R.drawable.ic_laptop),
            Category("Mouse", R.drawable.ic_mouse),
            Category("Console", R.drawable.ic_console)
        )
    }

    // Dummy Data for Featured Products
    private fun getFeaturedProducts(): List<Product> {
        return listOf(
            Product(
                name = "SSD NVME M.2",
                price = "1.000.000",
                category = "STORAGE",
                images = listOf(R.drawable.img_ssd, R.drawable.img_ssd, R.drawable.img_ssd),
                details = mapOf(
                    "Published" to "1 Week Ago",
                    "Category" to "Storage",
                    "Warranty" to "2 Years",
                    "Stock" to "15",
                    "Weight" to "100 grams"
                ),
                description = "SSD NVME M.2 with ultra-fast speed and high reliability. Perfect for gaming or professional use.",
                discussions = listOf(
                    Discussion("Kim Ji Won", R.drawable.profil_diskusi, "Bagus sekali, performanya mantap!", "2 Bulan yang lalu"),
                    Discussion("Lee Min Ho", R.drawable.profil_diskusi, "Apakah support untuk motherboard B450?", "1 Bulan yang lalu")
                )
            ),
            Product(
                name = "Keyboard",
                price = "1.200.000",
                category = "KEYBOARD",
                images = listOf(R.drawable.img_keyboard, R.drawable.img_keyboard2),
                details = mapOf(
                    "Published" to "2 Weeks Ago",
                    "Category" to "Accessories",
                    "Warranty" to "1 Year",
                    "Stock" to "25",
                    "Weight" to "700 grams"
                ),
                description = "High-quality mechanical keyboard with RGB lighting. Ideal for gamers and professionals.",
                discussions = listOf(
                    Discussion("Park Seo Joon", R.drawable.profil_diskusi, "Apakah keycaps-nya bisa diganti?", "1 Minggu yang lalu"),
                    Discussion("Kim Go Eun", R.drawable.profil_diskusi, "Bagus, tapi suara agak terlalu clicky.", "3 Hari yang lalu")
                )
            ),
            Product(
                name = "TMA-2 HD Wireless",
                price = "1.500.000",
                category = "GADGET",
                images = listOf(R.drawable.img_headset, R.drawable.img_headset),
                details = mapOf(
                    "Published" to "3 Days Ago",
                    "Category" to "Audio",
                    "Warranty" to "3 Years",
                    "Stock" to "10",
                    "Weight" to "250 grams"
                ),
                description = "Premium wireless headset with high-fidelity sound and long battery life.",
                discussions = listOf(
                    Discussion("Song Hye Kyo", R.drawable.profil_diskusi, "Suara sangat jernih, worth it!", "5 Hari yang lalu"),
                    Discussion("Hyun Bin", R.drawable.profil_diskusi, "Nyaman digunakan untuk waktu lama.", "1 Hari yang lalu")
                )
            )
        )
    }

    // Dummy Data for Laptop Collection
    private fun getLaptopCollection(): List<Product> {
        return listOf(
            Product(
                name = "ROG STRIX G16",
                price = "15.000.000",
                category = "LAPTOP",
                images = listOf(R.drawable.img_rog, R.drawable.img_rog),
                details = mapOf(
                    "Published" to "3 Days Ago",
                    "Category" to "Audio",
                    "Warranty" to "3 Years",
                    "Stock" to "10",
                    "Weight" to "250 grams"
                ),
                description = "Powerful gaming laptop with the latest hardware."
            ),
            Product(
                name = "MacBook Pro",
                price = "25.000.000",
                category = "LAPTOP",
                images = listOf(R.drawable.img_mac, R.drawable.img_mac),
                details = mapOf(
                    "Published" to "3 Days Ago",
                    "Category" to "Audio",
                    "Warranty" to "3 Years",
                    "Stock" to "10",
                    "Weight" to "250 grams"
                ),
                description = "High-end laptop for professionals."
            ),
            Product(
                name = "ASUS TUF",
                price = "10.000.000",
                category = "LAPTOP",
                images = listOf(R.drawable.img_tuf, R.drawable.img_tuf),
                details = mapOf(
                    "Published" to "3 Days Ago",
                    "Category" to "Audio",
                    "Warranty" to "3 Years",
                    "Stock" to "10",
                    "Weight" to "250 grams"
                ),
                description = "Affordable gaming laptop with great performance."
            )
        )
    }

    // Dummy Data for Under 20 Juta
    private fun getUnder20Products(): List<Product> {
        return listOf(
            Product(
                name = "PC Gaming RTX4090",
                price = "18.000.000",
                category = "PC",
                images = listOf(R.drawable.img_pc, R.drawable.img_pc),
                details = mapOf(
                    "Published" to "3 Days Ago",
                    "Category" to "Audio",
                    "Warranty" to "3 Years",
                    "Stock" to "10",
                    "Weight" to "250 grams"
                ),
                description = "High-performance gaming PC for enthusiasts."
            ),
            Product(
                name = "PC Fullset",
                price = "19.000.000",
                category = "PC",
                images = listOf(R.drawable.img_pc3, R.drawable.img_pc),
                details = mapOf(
                    "Published" to "3 Days Ago",
                    "Category" to "Audio",
                    "Warranty" to "3 Years",
                    "Stock" to "10",
                    "Weight" to "250 grams"
                ),
                description = "Complete PC setup for gaming and productivity."
            ),
            Product(
                name = "Camera Digital",
                price = "19.000.000",
                category = "GADGET",
                images = listOf(R.drawable.img_camera1, R.drawable.img_camera1),
                details = mapOf(
                    "Published" to "3 Days Ago",
                    "Category" to "Audio",
                    "Warranty" to "3 Years",
                    "Stock" to "10",
                    "Weight" to "250 grams"
                ),
                description = "Premium wireless headset with high-fidelity sound and long battery life."
            ),
            Product(
                name = "PC Siap Stream",
                price = "19.000.000",
                category = "PC",
                images = listOf(R.drawable.img_pc3, R.drawable.img_pc),
                details = mapOf(
                    "Published" to "3 Days Ago",
                    "Category" to "Audio",
                    "Warranty" to "3 Years",
                    "Stock" to "10",
                    "Weight" to "250 grams"
                ),
                description = "Stream-ready PC with the best performance."
            ),
            Product(
                name = "ROG STRIX G16",
                price = "17.500.000",
                category = "LAPTOP",
                images = listOf(R.drawable.img_rog, R.drawable.img_rog),
                details = mapOf(
                    "Published" to "3 Days Ago",
                    "Category" to "Audio",
                    "Warranty" to "3 Years",
                    "Stock" to "10",
                    "Weight" to "250 grams"
                ),
                description = "High-end gaming laptop."
            )
        )
    }

    private fun getProductByCategory(category: String): List<Product> {
        val allProducts = getFeaturedProducts() + getLaptopCollection() + getUnder20Products()
        return allProducts.filter { it.category.equals(category, ignoreCase = true) }
    }

    private fun onCategoryClick(category: Category) {
        val products = getProductByCategory(category.name)
        val intent = Intent(this, ProdukCategoryActivity::class.java).apply {
            putExtra("selected_category", category.name)
            putParcelableArrayListExtra("product_list", ArrayList(products))
        }
        startActivity(intent)
    }

    private fun onProductClick(product: Product) {
        val intent = Intent(this, ProductDetailActivity::class.java)
        intent.putExtra("selected_product", product)
        startActivity(intent)
    }

    private fun navigateToCart() {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }
}