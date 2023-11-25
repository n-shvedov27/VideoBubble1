package com.example.videobubble

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

private const val LOAD_VIDEO_DEBOUNCE_MS = 500L

class BubbleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), CoroutineScope {

    private var loadVideoJob: Job? = null
    private val mediaSourceFactory = MediaSourceFactory(itemView.context)
    private val playerView = itemView.findViewById<PlayerView>(R.id.li_bubble_player_view)
    private val player = ExoPlayer.Builder(itemView.context).build().apply {
        repeatMode = ExoPlayer.REPEAT_MODE_ONE
    }

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    init {
        playerView.player = player
    }

    fun bind(model: BubbleModel) {
        loadVideoJob?.cancel()
        loadVideoJob = launch {
            delay(LOAD_VIDEO_DEBOUNCE_MS)
            val mediaSource = mediaSourceFactory.createMediaSource(model.videoUrl)
            player.setMediaSource(mediaSource)
            player.prepare()
            player.play()
        }
    }
}