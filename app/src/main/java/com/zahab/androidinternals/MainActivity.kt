package com.zahab.androidinternals

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.zahab.androidinternals.ui.theme.AndroidInternalszahabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screenOffReceiver = ScreenOffReceiver()
        enableEdgeToEdge()

        ContextCompat.registerReceiver(
            applicationContext,
            screenOffReceiver,
            IntentFilter("android.intent.action.SCREEN_OFF"),
            ContextCompat.RECEIVER_EXPORTED

        )

        setContent {
            AndroidInternalszahabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Button(
                        onClick = {
                            Intent("com.zahab.ACTION_NOTIFY_VIA_BROADCAST").also {
                                it.`package` = "com.zahab.andriodinternals_zahab2"
                                sendBroadcast(it)
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                            .padding(innerPadding)
                    ) {
                        Text("Send Broadcast")
                    }

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Receiver Unregistered")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidInternalszahabTheme {
        Greeting("Android")
    }
}