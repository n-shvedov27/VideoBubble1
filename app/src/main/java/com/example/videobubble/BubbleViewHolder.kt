package com.example.videobubble

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

class BubbleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val mediaSourceFactory = MediaSourceFactory(itemView.context)
    private val playerView = itemView.findViewById<PlayerView>(R.id.li_bubble_player_view)
    private val player = ExoPlayer.Builder(itemView.context).build().apply {
        repeatMode = ExoPlayer.REPEAT_MODE_ONE
    }

    init {
        playerView.player = player
    }

    fun bind(model: BubbleModel) {
        val mediaSource = mediaSourceFactory.createMediaSource(model.videoUrl)
        player.setMediaSource(mediaSource)
        player.prepare()
        player.play()
    }
}