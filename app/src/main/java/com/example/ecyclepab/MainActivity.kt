package com.example.ecyclepab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set padding untuk menyesuaikan dengan insets (opsional)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout)) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBarsInsets.left,
                systemBarsInsets.top,
                systemBarsInsets.right,
                systemBarsInsets.bottom
            )
            insets
        }

        // Optional: Menghilangkan status bar untuk tampilan full screen
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = true // Status bar icon light mode
        }

        // Logika untuk tombol "Log in"
        findViewById<Button>(R.id.loginButton).setOnClickListener {
            // Mengarahkan ke halaman Login
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        // Logika untuk tombol "Sign up"
        findViewById<Button>(R.id.signUpButton).setOnClickListener {
            // Mengarahkan ke halaman Register
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        // Logika untuk tombol "Google"
        findViewById<Button>(R.id.googleButton).setOnClickListener {
            // Tambahkan logika untuk login dengan Google
        }

        // Logika untuk tombol "Facebook"
        findViewById<Button>(R.id.facebookButton).setOnClickListener {
            // Tambahkan logika untuk login dengan Facebook
        }
    }
}
