package com.example.ecyclepab

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SupportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        val btnBack: ImageButton = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        val supportMessageInput: EditText = findViewById(R.id.supportMessageInput)
        val sendSupportButton: Button = findViewById(R.id.sendSupportButton)

        sendSupportButton.setOnClickListener {
            val message = supportMessageInput.text.toString()
            if (message.isNotEmpty()) {
                Toast.makeText(this, "Message sent: $message", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show()
            }
        }
    }
}