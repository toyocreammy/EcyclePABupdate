package com.example.ecyclepab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        // Initialize user profile info
        val userName: TextView = findViewById(R.id.userName)
        val userEmail: TextView = findViewById(R.id.userEmail)
        val userPhone: TextView = findViewById(R.id.userPhone)

        // Set user info
        userName.text = "Sakti Gyatt"
        userEmail.text = "Gilbertjones001@gmail.com"
        userPhone.text = "0812562232"

        // Edit Profile Button Click
        val editProfile: TextView = findViewById(R.id.editProfile)
        editProfile.setOnClickListener {
            Toast.makeText(this, "Edit Profile clicked", Toast.LENGTH_SHORT).show()
        }

        // Menu Button Clicks
        findViewById<Button>(R.id.saldoButton).setOnClickListener {
            val intent = Intent(this, BalanceActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.addressButton).setOnClickListener {
            val intent = Intent(this, AddressMenuActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.kelolaTokoButton).setOnClickListener {
            val intent = Intent(this, KelolaTokoActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.supportButton).setOnClickListener {
            val intent = Intent(this, SupportActivity::class.java)
            startActivity(intent)
        }

        // Sign Out Button Click
        findViewById<TextView>(R.id.signOutButton).setOnClickListener {
            Toast.makeText(this, "Sign Out clicked", Toast.LENGTH_SHORT).show()
        }
    }
}