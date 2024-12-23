package com.example.ecyclepab.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecyclepab.R
import com.example.ecyclepab.data.Discussion

class DiscussionAdapter(private val discussionList: List<Discussion>) :
    RecyclerView.Adapter<DiscussionAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val profileImage: ImageView = view.findViewById(R.id.profile_image)
        val userName: TextView = view.findViewById(R.id.user_name)
        val date: TextView = view.findViewById(R.id.date)
        val discussionText: TextView = view.findViewById(R.id.discussion_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_discussion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val discussion = discussionList[position]
        holder.userName.text = discussion.userName // Field name dari data.Discussion
        holder.date.text = discussion.date    // Field date dari data.Discussion
        holder.discussionText.text = discussion.comment // Field message dari data.Discussion
        holder.profileImage.setImageResource(discussion.profileImage) // Field profileImage dari data.Discussion
    }

    override fun getItemCount(): Int = discussionList.size
}
