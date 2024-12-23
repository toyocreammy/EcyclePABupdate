package com.example.ecyclepab

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecyclepab.adapter.DiscussionAdapter
import com.example.ecyclepab.data.Discussion

class DetailDiskusiActivity : AppCompatActivity() {

    private lateinit var discussionAdapter: DiscussionAdapter
    private val discussionList: MutableList<Discussion> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_diskusi)

        // Ambil data diskusi dari Intent
        val discussions = intent.getParcelableArrayListExtra<Discussion>("discussion_list") ?: arrayListOf()
        discussionList.addAll(discussions) // Tambahkan data dari Intent ke list

        // Atur RecyclerView untuk diskusi
        val recyclerView = findViewById<RecyclerView>(R.id.discussionRecyclerView)
        discussionAdapter = DiscussionAdapter(discussionList) // Inisialisasi adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = discussionAdapter

        // Tombol kembali
        findViewById<ImageView>(R.id.back_button).setOnClickListener {
            finish()
        }

        // Tombol kirim komentar
        val sendButton = findViewById<ImageView>(R.id.sendButton)
        val inputField = findViewById<EditText>(R.id.discussionInputField)

        sendButton.setOnClickListener {
            val newComment = inputField.text.toString().trim()
            if (newComment.isNotEmpty()) {
                val newDiscussion = Discussion(
                    userName = "You", // Nama pengguna default
                    profileImage = R.drawable.profil_diskusi, // Gambar profil default
                    comment = newComment,
                    date = "Just now" // Waktu default
                )
                discussionList.add(newDiscussion) // Tambahkan diskusi baru ke list
                discussionAdapter.notifyItemInserted(discussionList.size - 1) // Beri tahu adapter
                recyclerView.scrollToPosition(discussionList.size - 1) // Scroll ke komentar baru
                inputField.text.clear() // Kosongkan input field
            } else {
                Toast.makeText(this, "Komentar tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
