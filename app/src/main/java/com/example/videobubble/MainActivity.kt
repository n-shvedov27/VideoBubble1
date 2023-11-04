package com.example.videobubble

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val items = mutableListOf<BubbleModel>().apply {
        repeat(10) {
            add(BubbleModel(R.drawable.ic_grade))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.bubbles_rv)
        recyclerView.adapter = BubbleAdapter(items)
    }
}