package com.zahab.androidinternals

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ScreenOffReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        println(
            "Screen Off Receiver"
        )
    }
}