package com.zahab.androidinternals.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MusicService : Service() {

    val binder = MusicServiceBinder()
    val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())


    private val _currentSong = MutableStateFlow(0)

    val currentSong = _currentSong
        .map { songs[it] }
        .stateIn(
            scope = serviceScope,
            started = SharingStarted.Lazily,
            initialValue = songs[_currentSong.value]
        )

    fun next() {
        if (_currentSong.value == songs.lastIndex)
            _currentSong.value = 0
        else
            _currentSong.value++
    }


    fun previous() {
        if (_currentSong.value == 0)
            _currentSong.value = songs.lastIndex
        else
            _currentSong.value--
    }



    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    inner class MusicServiceBinder : Binder() {
        fun getMusicService() = this@MusicService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }
}

private val songs = listOf(
    "Song1",
    "Song2",
    "Song3",
    "Song4",
    "Song5",
    "Song6",
)
