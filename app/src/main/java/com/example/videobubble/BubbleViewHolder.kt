package com.example.videobubble

import android.animation.ValueAnimator
import android.view.View
import android.widget.ImageView
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

private const val LOAD_VIDEO_DEBOUNCE_MS = 700L

class BubbleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), CoroutineScope {

    private val listener = BubblePlayerListener(this)
    private val thumbnail = itemView.findViewById<ImageView>(R.id.li_bubble_thumbnail)
    private var loadVideoJob: Job? = null
    private val mediaSourceFactory = MediaSourceFactory(itemView.context)
    private val playerView = itemView.findViewById<PlayerView>(R.id.li_bubble_player_view)
    private val player = ExoPlayer.Builder(itemView.context).build().apply {
        repeatMode = ExoPlayer.REPEAT_MODE_ONE
        addListener(listener)
    }
    
    var isActive = false

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    init {
        playerView.player = player
    }

    fun bind(model: BubbleModel) {
        onStartLoadVideo(model.videoUrl)
        
        loadVideoJob?.cancel()
        loadVideoJob = launch {
            delay(LOAD_VIDEO_DEBOUNCE_MS)
            val mediaSource = mediaSourceFactory.createMediaSource(model.videoUrl)
            player.setMediaSource(mediaSource)
            player.prepare()
            player.play()
        }
    }
    
    fun onFinishLoadVideo() {
        thumbnail.isVisible = false
    }
    
    fun makeInactiveAnimated() {
        isActive = false
        val initialSize = itemView.context.resources.getDimensionPixelSize(R.dimen.bubble_initial_size)
        ValueAnimator.ofInt(playerView.height, initialSize).apply {
            duration = 500L
            interpolator = PathInterpolatorCompat.create(0.33F, 0F, 0F, 1F)
            addUpdateListener {
                playerView.updateLayoutParams {
                    height = it.animatedValue as Int
                    width = it.animatedValue as Int
                }
            }
        }.start()
    }

    fun makeInactiveImmediately() {
        isActive = false
        val initialSize = itemView.context.resources.getDimensionPixelSize(R.dimen.bubble_initial_size)
        playerView.updateLayoutParams {
            height = initialSize
            width = initialSize
        }
    }
    
    fun makeActive() {
        isActive = true
        val expandedSize = itemView.context.resources.getDimensionPixelSize(R.dimen.bubble_expanded_size)
        ValueAnimator.ofInt(playerView.height, expandedSize).apply {
            duration = 500L
            interpolator = PathInterpolatorCompat.create(0.33F, 0F, 0F, 1F)
            addUpdateListener {
                playerView.updateLayoutParams {
                    height = it.animatedValue as Int
                    width = it.animatedValue as Int
                }
            }
        }.start()
    }
    
    private fun onStartLoadVideo(videoUrl: String) {
        thumbnail.isVisible = true
        Glide.with(thumbnail)
            .load(videoUrl)
            .apply(RequestOptions().override(200, 200))
            .into(thumbnail)
    }
}