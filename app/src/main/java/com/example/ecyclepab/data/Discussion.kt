package com.example.ecyclepab.data
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Discussion(
    val userName: String,
    val profileImage: Int, // Gambar profil pengguna
    val comment: String, // Isi komentar
    val date: String // Waktu komentar
) : Parcelable
