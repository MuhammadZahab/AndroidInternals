package com.zahab.androidinternals

import android.app.Application
import com.zahab.androidinternals.services.MusicServiceController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {

    private val applicatiokScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    lateinit var musicServiceController: MusicServiceController
    override fun onCreate() {
        super.onCreate()

        musicServiceController = MusicServiceController(
            context = this,
            coroutineScope = applicatiokScope
        )

    }
}