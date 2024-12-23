package com.example.ecyclepab

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Login : AppCompatActivity() {
    private var isPasswordVisible = false // Variabel untuk toggle password visibility
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inisialisasi FirebaseAuth dan Firestore
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Show/Hide Password
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val showPasswordButton = findViewById<ImageButton>(R.id.showPasswordButton)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signUpTextView = findViewById<TextView>(R.id.signUpTextView)

        showPasswordButton.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            passwordEditText.inputType = if (isPasswordVisible) {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            passwordEditText.setSelection(passwordEditText.text.length)
        }

        // Login Button
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Firebase Authentication untuk login
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid ?: ""
                        fetchUserData(userId)
                    } else {
                        Toast.makeText(
                            this,
                            "Login failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        // Sign Up TextView
        signUpTextView.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }

    private fun fetchUserData(userId: String) {
        // Ambil data pengguna dari Firestore
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val userName = document.getString("name") ?: ""
                    val userEmail = document.getString("email") ?: ""

                    // Simpan data pengguna ke SharedPreferences
                    saveUserToPreferences(userId, userName, userEmail)

                    // Pindah ke halaman utama
                    Toast.makeText(this, "Welcome back, $userName!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "User data not found in Firestore", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to fetch user data: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveUserToPreferences(userId: String, userName: String, userEmail: String) {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("user_id", userId)
            putString("user_name", userName)
            putString("user_email", userEmail)
            apply()
        }
    }
}
