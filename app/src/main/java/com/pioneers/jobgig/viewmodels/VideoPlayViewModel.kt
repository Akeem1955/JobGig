package com.pioneers.jobgig.viewmodels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class VideoPlayViewModel(private val saveState:SavedStateHandle):ViewModel() {
    private var uris = MutableStateFlow<List<Uri>>(emptyList())
    private var mediaItems = uris.map { uris ->
        uris.map {uri ->
            MediaItem.fromUri(uri)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private var player:ExoPlayer? = null
    fun player(context:Context):ExoPlayer{
       return _player(context).also { it.prepare()}
    }
    private fun _player(context:Context):ExoPlayer{
        if (player != null)
            return player as ExoPlayer
        else
            player = ExoPlayer.Builder(context).build()
        return player as ExoPlayer
    }

    private fun addVideoUri(uri: Uri){
        saveState["uris"] =  uris.value + uri
        player?.addMediaItem(MediaItem.fromUri(uri))
    }

    fun playVideo(position:Int){
        player?.setMediaItem(mediaItems.value[position])
    }



}