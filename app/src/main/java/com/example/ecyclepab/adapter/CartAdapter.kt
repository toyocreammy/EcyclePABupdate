package com.example.ecyclepab.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecyclepab.R
import com.example.ecyclepab.data.Product

class CartAdapter(private val cartItems: List<Product>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkboxSeller: CheckBox = itemView.findViewById(R.id.checkboxSeller)
        val txtSellerName: TextView = itemView.findViewById(R.id.txtSellerName)
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        val txtProductName: TextView = itemView.findViewById(R.id.txtProductName)
        val txtProductPrice: TextView = itemView.findViewById(R.id.txtProductPrice)
        val btnDecrease: ImageButton = itemView.findViewById(R.id.btnDecrease)
        val txtQuantity: TextView = itemView.findViewById(R.id.txtQuantity)
        val btnIncrease: ImageButton = itemView.findViewById(R.id.btnIncrease)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartItems[position]

        // Set data ke view
        holder.txtSellerName.text = "Vino" // Ganti sesuai kebutuhan
        holder.txtProductName.text = product.name
        holder.txtProductPrice.text = "Rp ${product.price}"
        holder.imgProduct.setImageResource(product.images[0]) // Pastikan list image tidak kosong
        holder.txtQuantity.text = product.quantity.toString()

        holder.btnDecrease.setOnClickListener {
            if (product.quantity > 1) {
                product.quantity--
                holder.txtQuantity.text = product.quantity.toString()
            }
        }

        holder.btnIncrease.setOnClickListener {
            product.quantity++
            holder.txtQuantity.text = product.quantity.toString()
        }

        // Checkbox Seller
        holder.checkboxSeller.setOnCheckedChangeListener { _, isChecked ->
            product.isSelected = isChecked
        }
    }

    override fun getItemCount(): Int = cartItems.size
}

