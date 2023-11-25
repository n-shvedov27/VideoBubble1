package com.example.videobubble

import android.view.View
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.recyclerview.widget.RecyclerView

private const val CACHE_SIZE = 7

class ViewHolderPool(recyclerView: RecyclerView) {
    
    private var inflater = AsyncLayoutInflater(recyclerView.context)
    private var viewCache = ArrayDeque<View>()
    
    init {
        for (i in 0 until CACHE_SIZE) {
            inflater.inflate(R.layout.li_bubble, recyclerView) { view, _, _ ->
                viewCache.add(view)
            }
        }
    }

    fun getOrNull(): View? {
        return viewCache.removeFirstOrNull()
    }
}