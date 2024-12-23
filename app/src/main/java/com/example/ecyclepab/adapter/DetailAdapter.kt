package com.example.ecyclepab.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecyclepab.R

class DetailAdapter(private val details: List<Pair<String, String>>) :
    RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val labelTextView: TextView = view.findViewById(R.id.detail_label)
        val valueTextView: TextView = view.findViewById(R.id.detail_value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (label, value) = details[position]
        holder.labelTextView.text = label
        holder.valueTextView.text = ": $value"
    }

    override fun getItemCount(): Int = details.size
}
