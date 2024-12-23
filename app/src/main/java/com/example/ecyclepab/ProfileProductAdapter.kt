package com.example.ecyclepab.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecyclepab.R
import com.example.ecyclepab.models.Product

class ProfileProductAdapter(private val productList: ArrayList<Product>) :
    RecyclerView.Adapter<ProfileProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val productRating: RatingBar = itemView.findViewById(R.id.product_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wishlistitem_product, parent, false) // Pastikan ini menggunakan item yang benar
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = productList[position]
        holder.productName.text = currentProduct.name
        holder.productPrice.text = currentProduct.price
        holder.productRating.rating = currentProduct.rating.toFloat()
        holder.productImage.setImageResource(currentProduct.imageResId) // Pastikan ini adalah int
    }

    override fun getItemCount() = productList.size
}