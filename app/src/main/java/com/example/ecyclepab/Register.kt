package com.example.ecyclepab

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Register : AppCompatActivity() {
    private var isPasswordVisible = false
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inisialisasi FirebaseAuth dan Firestore
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this@Register, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val showPasswordButton = findViewById<ImageButton>(R.id.showPasswordButton)
        val registerButton = findViewById<Button>(R.id.registerButton)

        showPasswordButton.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            passwordEditText.inputType = if (isPasswordVisible) {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            passwordEditText.setSelection(passwordEditText.text.length)
        }

        registerButton.setOnClickListener {
            val fullName = findViewById<EditText>(R.id.fullNameEditText).text.toString()
            val email = findViewById<EditText>(R.id.emailEditText).text.toString()
            val password = passwordEditText.text.toString()

            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerUser(fullName, email, password)
        }
    }

    private fun registerUser(fullName: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                    saveUserToFirestore(userId, fullName, email)
                } else {
                    Toast.makeText(this, "Signup failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserToFirestore(userId: String, fullName: String, email: String) {
        val userMap = hashMapOf(
            "id" to userId,
            "name" to fullName,
            "email" to email,
            "created_at" to System.currentTimeMillis()
        )

        db.collection("users").document(userId)
            .set(userMap)
            .addOnSuccessListener {
                saveUserToPreferences(userId, fullName, email)
                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save data: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveUserToPreferences(userId: String, fullName: String, email: String) {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("user_id", userId)
            putString("user_name", fullName)
            putString("user_email", email)
            apply()
        }
    }
}
