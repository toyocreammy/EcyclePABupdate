package com.example.ecyclepab

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
class ExploreActivity : AppCompatActivity() {

    private lateinit var adapter: ExploreAdapter
    private lateinit var searchInput: EditText
    private lateinit var clearButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var onSaleButton: Button
    private lateinit var priceButton: Button
    private lateinit var sortByButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)

        // Inisialisasi elemen UI
        searchInput = findViewById(R.id.searchInput)
        clearButton = findViewById(R.id.clearButton)
        recyclerView = findViewById(R.id.recyclerView)
        onSaleButton = findViewById(R.id.onSaleButton)
        priceButton = findViewById(R.id.priceButton)
        sortByButton = findViewById(R.id.sortByButton)

        // Set up RecyclerView dengan GridLayoutManager untuk 2 kolom
        val productList = getProductList()
        adapter = ExploreAdapter(productList)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // 2 kolom
        recyclerView.adapter = adapter

        // Fungsi pencarian
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                adapter.filter(query)
                clearButton.visibility = if (query.isNotEmpty()) View.VISIBLE else View.GONE
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        clearButton.setOnClickListener {
            searchInput.text.clear()
        }

        // On Sale Button Functionality
        onSaleButton.setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            popupMenu.menu.add("On Sale")
            popupMenu.menu.add("New")
            popupMenu.menu.add("Popular")
            popupMenu.setOnMenuItemClickListener { menuItem ->
                filterByCategory(menuItem.title.toString())
                true
            }
            popupMenu.show()
        }

        // Price Button Functionality
        priceButton.setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            popupMenu.menu.add("Rp. 50.000 - 100.000")
            popupMenu.menu.add("Rp. 150.000 - 300.000")
            popupMenu.menu.add("Rp. 1.000.000 - 5.000.000")
            popupMenu.setOnMenuItemClickListener { menuItem ->
                filterByPrice(menuItem.title.toString())
                true
            }
            popupMenu.show()
        }

        // Sort By Button Functionality
        sortByButton.setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            popupMenu.menu.add("Gaming")
            popupMenu.menu.add("Office")
            popupMenu.menu.add("Home Electronic")
            popupMenu.setOnMenuItemClickListener { menuItem ->
                filterBySort(menuItem.title.toString())
                true
            }
            popupMenu.show()
        }
    }

    private fun filterByCategory(category: String) {
        val filteredList = when (category) {
            "On Sale" -> getProductList().filter { it.reviews > 10 } // Example filter logic
            "New" -> getProductList().filter { it.rating > 4.5 }
            "Popular" -> getProductList().filter { it.reviews > 15 }
            else -> getProductList()
        }
        adapter.updateList(filteredList)
    }

    private fun filterByPrice(priceRange: String) {
        val filteredList = when (priceRange) {
            "Rp. 50.000 - 100.000" -> getProductList().filter {
                it.price.replace("Rp. ", "").replace(".", "").toInt() in 50000..100000
            }
            "Rp. 150.000 - 300.000" -> getProductList().filter {
                it.price.replace("Rp. ", "").replace(".", "").toInt() in 150000..300000
            }
            "Rp. 1.000.000 - 5.000.000" -> getProductList().filter {
                it.price.replace("Rp. ", "").replace(".", "").toInt() in 1000000..5000000
            }
            else -> getProductList()
        }
        adapter.updateList(filteredList)
    }

    private fun filterBySort(sortType: String) {
        val sortedList = when (sortType) {
            "Gaming" -> getProductList().filter { it.name.contains("Gaming", true) }
            "Office" -> getProductList().filter { it.name.contains("Office", true) }
            "Home Electronic" -> getProductList().filter { it.name.contains("Electronic", true) }
            else -> getProductList()
        }
        adapter.updateList(sortedList)
    }

    private fun getProductList(): List<Product> {
        return listOf(
            Product("TMA-2 HD Wireless", "Rp. 1.500.000", 4.6, 12, R.drawable.headphone),
            Product("Keyboard", "Rp. 1.200.000", 4.3, 10, R.drawable.keyboard),
            Product("SSD NVME M.2", "Rp. 1.000.000", 4.4, 8, R.drawable.ssd),
            Product("Logitech G304", "Rp. 1.500.000", 4.7, 20, R.drawable.mouse1),
            Product("Victus 15asd1564", "Rp. 16.500.000", 4.9, 30, R.drawable.victus),
            Product("Carbon Keyboard Logi G54", "Rp. 1.700.000", 4.7, 24, R.drawable.keyboardcarbon)
        )
    }
}