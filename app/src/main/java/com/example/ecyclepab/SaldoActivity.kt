package com.example.ecyclepab

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SaldoActivity : AppCompatActivity() {

    private var saldo: Int = 0
    private val transactionHistory: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saldo)

        val btnBack: ImageButton = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        val saldoAmount: TextView = findViewById(R.id.saldoAmount)
        updateSaldoDisplay(saldoAmount)

        val topupAmountInput: EditText = findViewById(R.id.topupAmountInput)

        val topupButton: Button = findViewById(R.id.topupButton)
        topupButton.setOnClickListener {
            val topupAmount = topupAmountInput.text.toString().toIntOrNull()
            if (topupAmount != null && topupAmount > 0) {
                saldo += topupAmount
                transactionHistory.add("Top Up: Rp$topupAmount")
                updateSaldoDisplay(saldoAmount)
                topupAmountInput.text.clear()
                Toast.makeText(this, "Top up berhasil!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Masukkan jumlah yang valid!", Toast.LENGTH_SHORT).show()
            }
        }

        val withdrawButton: Button = findViewById(R.id.withdrawButton)
        withdrawButton.setOnClickListener {
            val withdrawAmount = topupAmountInput.text.toString().toIntOrNull()
            if (withdrawAmount != null && withdrawAmount > 0 && withdrawAmount <= saldo) {
                saldo -= withdrawAmount
                transactionHistory.add("Withdraw: Rp$withdrawAmount")
                updateSaldoDisplay(saldoAmount)
                topupAmountInput.text.clear()
                Toast.makeText(this, "Penarikan berhasil!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Jumlah penarikan tidak valid!", Toast.LENGTH_SHORT).show()
            }
        }

        val transactionRecyclerView: RecyclerView = findViewById(R.id.recycler_view)
        transactionRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = TransactionAdapter(transactionHistory)
        transactionRecyclerView.adapter = adapter
    }

    private fun updateSaldoDisplay(saldoAmount: TextView) {
        saldoAmount.text = "Rp$saldo"
    }
}