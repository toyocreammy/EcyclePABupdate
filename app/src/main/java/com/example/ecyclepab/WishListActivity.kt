package com.example.ecyclepab

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecyclepab.adapters.WishListAdapter
import com.example.ecyclepab.models.WishListItem

class WishListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var wishListAdapter: WishListAdapter
    private lateinit var addButton: Button
    private val wishListItems = mutableListOf<WishListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        recyclerView = findViewById(R.id.recyclerView)
        addButton = findViewById(R.id.addItemButton)

        recyclerView.layoutManager = LinearLayoutManager(this)
        wishListAdapter = WishListAdapter(wishListItems)
        recyclerView.adapter = wishListAdapter

        // Dummy data
        wishListItems.add(WishListItem("Acer Aspire 1544", "Rp. 5.600.000", R.drawable.item1))
        wishListItems.add(WishListItem("Keyboard Titan Gaming Pro", "Rp. 600.000", R.drawable.item2))

        wishListAdapter.notifyDataSetChanged()

        addButton.setOnClickListener {
            val newItem = WishListItem("New Item", "$30.00", R.drawable.orang)
            wishListItems.add(newItem)
            wishListAdapter.notifyItemInserted(wishListItems.size - 1)
        }
    }
}