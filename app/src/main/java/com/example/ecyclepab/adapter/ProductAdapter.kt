package com.example.ecyclepab.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecyclepab.ProductDetailActivity
import com.example.ecyclepab.R
import com.example.ecyclepab.data.Product

class ProductAdapter(
    private val products: List<Product>,
    private val onProductClick: (Product) -> Unit // Callback untuk menangani klik produk
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.product_image)
        val productName: TextView = view.findViewById(R.id.product_name)
        val productPrice: TextView = view.findViewById(R.id.product_price)

        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onProductClick(products[position]) // Panggil callback saat produk diklik
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.productImage.setImageResource(product.images.firstOrNull() ?: R.drawable.default_image)
        holder.productName.text = product.name
        holder.productPrice.text = "Rp ${product.price}"
    }

    override fun getItemCount(): Int = products.size
}

