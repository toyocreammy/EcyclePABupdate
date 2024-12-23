package com.example.ecyclepab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderHistoryAdapter(private var orderList: List<Order>) : RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder>() {

    // ViewHolder untuk setiap item produk
    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.product_image)
        val productName: TextView = view.findViewById(R.id.product_name)
        val productPrice: TextView = view.findViewById(R.id.product_price)
        val productStatus: TextView = view.findViewById(R.id.product_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.productImage.setImageResource(order.productImageResId)
        holder.productName.text = order.productName
        holder.productPrice.text = "Rp ${String.format("%,.0f", order.price)}"
        holder.productStatus.text = order.status

        // Warna status: Oranye untuk "Dikirim", Hijau untuk "Selesai"
        holder.productStatus.setTextColor(
            if (order.status == "Dikirim") android.graphics.Color.parseColor("#FFA000")
            else android.graphics.Color.parseColor("#4CAF50")
        )
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    // Fungsi untuk memperbarui data dalam adapter
    fun updateData(newList: List<Order>) {
        orderList = newList
        notifyDataSetChanged() // Beritahu adapter bahwa datanya berubah
    }
}