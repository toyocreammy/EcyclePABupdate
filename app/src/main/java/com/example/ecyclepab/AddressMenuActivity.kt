package com.example.ecyclepab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class AddressMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_menu)

        // Back Button functionality
        val btnBack: ImageButton = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressed() // Go back to the previous screen
        }

        // Handle Edit Address buttons
        findViewById<Button>(R.id.editAddressButton1).setOnClickListener {
            navigateToAddressActivity(1)
        }
        findViewById<Button>(R.id.editAddressButton2).setOnClickListener {
            navigateToAddressActivity(2)
        }

        // Handle Add New Address button
        findViewById<Button>(R.id.addNewAddressButton).setOnClickListener {
            navigateToAddressActivity(-1) // -1 indicates a new address
        }
    }

    private fun navigateToAddressActivity(addressId: Int) {
        val intent = Intent(this, AddressActivity::class.java)
        intent.putExtra("addressId", addressId)
        startActivity(intent)
    }
}