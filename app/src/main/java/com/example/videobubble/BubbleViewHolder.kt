package com.example.videobubble

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class BubbleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageView = itemView.findViewById<ImageView>(R.id.bubble_image)

    fun bind(model: BubbleModel) {
        imageView.setImageResource(model.imageRes)

    }
}