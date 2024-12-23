package com.example.ecyclepab.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecyclepab.R
import com.example.ecyclepab.models.WishListItem

class WishListAdapter(private val items: List<WishListItem>) :
    RecyclerView.Adapter<WishListAdapter.WishListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wishlist, parent, false)
        return WishListViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishListViewHolder, position: Int) {
        val item = items[position]
        holder.itemName.text = item.name
        holder.itemPrice.text = item.price
        holder.itemImage.setImageResource(item.imageResource)
    }

    override fun getItemCount(): Int = items.size

    class WishListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.itemName)
        val itemPrice: TextView = view.findViewById(R.id.itemPrice)
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
    }
}