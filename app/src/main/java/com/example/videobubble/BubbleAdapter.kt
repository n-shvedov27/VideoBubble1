package com.example.videobubble

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BubbleAdapter(
    private val items: List<BubbleModel>
) : RecyclerView.Adapter<BubbleViewHolder>() {

    private var viewHolderPool: ViewHolderPool? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        viewHolderPool = viewHolderPool ?: ViewHolderPool(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        viewHolderPool = null
    }
    
    private fun inflateNewView(parentView: ViewGroup): View {
        val inflater = LayoutInflater.from(parentView.context)
        return inflater.inflate(R.layout.li_bubble, parentView, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BubbleViewHolder {
        val cachedView = viewHolderPool?.getOrNull()
        val inflatedView = cachedView ?: inflateNewView(parent)
        return BubbleViewHolder(inflatedView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BubbleViewHolder, position: Int) {
        holder.bind(items[position])
    }
}