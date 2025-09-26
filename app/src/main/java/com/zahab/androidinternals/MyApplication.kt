package com.zahab.androidinternals

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {

    private val applicatiokScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
}