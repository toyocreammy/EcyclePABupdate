package com.example.ecyclepab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Product(val name: String, val price: String, val rating: Double, val reviews: Int, val imageResource: Int)

class ExploreAdapter(private val originalList: List<Product>) : RecyclerView.Adapter<ExploreAdapter.ProductViewHolder>() {

    private var filteredList: List<Product> = originalList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.explore_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = filteredList[position]
        holder.productName.text = product.name
        holder.productPrice.text = product.price
        holder.productRating.text = "⭐ ${product.rating} | ${product.reviews} Diskusi"
        holder.productImage.setImageResource(product.imageResource)
    }

    override fun getItemCount() = filteredList.size

    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            originalList
        } else {
            originalList.filter { it.name.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }

    fun updateList(newList: List<Product>) {
        filteredList = newList
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val productRating: TextView = itemView.findViewById(R.id.productRating)
        val productImage: ImageView = itemView.findViewById(R.id.productImage)
    }
}