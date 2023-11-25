package com.example.videobubble

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BubbleAdapter(
    private val items: List<BubbleModel>
) : RecyclerView.Adapter<BubbleViewHolder>() {

    private var recyclerView: RecyclerView? = null
    private var viewHolderPool: ViewHolderPool? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        viewHolderPool = viewHolderPool ?: ViewHolderPool(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
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
        holder.itemView.setOnClickListener {
            findActiveViewHolder()?.makeInactiveAnimated()
            holder.makeActive()
        }
    }
    
    private fun findActiveViewHolder(): BubbleViewHolder? {
        recyclerView?.let { rv ->
            val layoutManager = rv.layoutManager as LinearLayoutManager
            val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
            val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()

            for (i in firstVisiblePosition ..  lastVisiblePosition) {
                val viewHolder = rv.findViewHolderForAdapterPosition(i) as? BubbleViewHolder
                if (viewHolder?.isActive == true) {
                    return viewHolder
                }
            }
        }
        return null
    }

    override fun onViewDetachedFromWindow(holder: BubbleViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.makeInactiveImmediately()
    }
}