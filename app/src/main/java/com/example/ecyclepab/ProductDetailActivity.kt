package com.example.ecyclepab

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.ecyclepab.adapter.DetailAdapter
import com.example.ecyclepab.adapter.DiscussionAdapter
import com.example.ecyclepab.adapter.ImageSliderAdapter
import com.example.ecyclepab.data.Product
import com.example.ecyclepab.CartManager

class ProductDetailActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        // Retrieve the Product object from the Intent
        @Suppress("DEPRECATION") val product = intent.getParcelableExtra<Product>("selected_product")

        // If product is not null, display its data
        product?.let {
            // Display product details
            findViewById<TextView>(R.id.product_name).text = it.name
            findViewById<TextView>(R.id.product_price).text = "Rp ${it.price}"
            findViewById<TextView>(R.id.product_description).text = it.description

            // Setup Image Slider
            val imageSlider = findViewById<ViewPager2>(R.id.product_image_slider)
            if (it.images.isNotEmpty()) {
                imageSlider.adapter = ImageSliderAdapter(it.images)
            } else {
                Toast.makeText(this, "No images available for this product.", Toast.LENGTH_SHORT).show()
            }

            // Setup RecyclerView for Details
            val detailRecyclerView = findViewById<RecyclerView>(R.id.recycler_detail)
            detailRecyclerView.layoutManager = LinearLayoutManager(this)
            detailRecyclerView.adapter = DetailAdapter(it.details.toList())

            // Setup RecyclerView for Discussions
            val discussionRecyclerView = findViewById<RecyclerView>(R.id.recycler_discussion)
            discussionRecyclerView.layoutManager = LinearLayoutManager(this)
            discussionRecyclerView.adapter = DiscussionAdapter(it.discussions)

            // Setup "See All" button to navigate to DetailDiskusiActivity
            val discussions = product?.discussions ?: emptyList()
            findViewById<TextView>(R.id.see_all).setOnClickListener {
                android.util.Log.d("ProductDetailActivity", "Discussions passed: $discussions")
                val intent = Intent(this, DetailDiskusiActivity::class.java)
                intent.putParcelableArrayListExtra("discussion_list", ArrayList(discussions))
                startActivity(intent)
            }


            // Button Click Handlers
            setupButtonActions(it)
        } ?: run {
            // If product data is not available, show an error and finish activity
            Toast.makeText(this, "Product data not available", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupButtonActions(product: Product) {
        // Back button
        findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }

        // Share button
        findViewById<ImageView>(R.id.ic_share).setOnClickListener {
            shareProduct(product.name, product.price)
        }

        // Cart button
        findViewById<ImageView>(R.id.cart_icon).setOnClickListener {
            navigateToCart()
        }

        // Buy Now button
        findViewById<Button>(R.id.buy_now_button).setOnClickListener {
            buyNow(product.name)
        }

        // Add to Cart button
        findViewById<Button>(R.id.cart_button).setOnClickListener {
            addToCart(product) // Mengirimkan objek Product
        }
    }


    private fun shareProduct(productName: String, productPrice: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "Check out this product: $productName, Price: $productPrice")
        }
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    private fun navigateToCart() {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }


    private fun buyNow(productName: String) {
        Toast.makeText(this, "Buying $productName now!", Toast.LENGTH_SHORT).show()
        // Logic for buying product
    }

    private fun addToCart(product: Product) {
        val cartManager = CartManager(this) // Menginisialisasi CartManager
        cartManager.addToCart(product) // Menambahkan produk ke keranjang
        Toast.makeText(this, "${product.name} telah ditambahkan ke keranjang.", Toast.LENGTH_SHORT).show()
    }

}
