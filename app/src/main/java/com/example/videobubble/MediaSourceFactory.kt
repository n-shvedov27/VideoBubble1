package com.example.videobubble

import android.content.Context
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSink
import com.google.android.exoplayer2.upstream.cache.CacheDataSource

internal class MediaSourceFactory(
    private val context: Context
) {

    private val mediaSourceFactory by lazy {
        val cacheSink = CacheDataSink.Factory().setCache(VideoCache.getInstance(context))
        val upstreamFactory = DefaultDataSource.Factory(context, DefaultHttpDataSource.Factory())
        val cacheDataSourceFactory = CacheDataSource.Factory()
            .setCacheWriteDataSinkFactory(cacheSink)
            .setCache(VideoCache.getInstance(context))
            .setUpstreamDataSourceFactory(upstreamFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
        ProgressiveMediaSource.Factory(cacheDataSourceFactory)
    }

    fun createMediaSource(url: String): MediaSource {
        return mediaSourceFactory.createMediaSource(MediaItem.fromUri(url))
    }
}