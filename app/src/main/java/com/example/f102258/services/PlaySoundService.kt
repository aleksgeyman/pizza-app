package com.example.f102258.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import com.example.f102258.R

class PlaySoundService : Service() {
    private lateinit var player: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, R.raw.click)
        player.isLooping = false
    }

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        player.start()
        Log.i("Sound Service", "START")
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        Log.i("Sound Service", "STOP")
    }
}