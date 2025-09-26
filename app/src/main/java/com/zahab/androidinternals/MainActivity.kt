package com.zahab.androidinternals


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.plcoding.androidinternals.Crypto
import com.zahab.androidinternals.ui.theme.AndroidInternalszahabTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidInternalszahabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        var encrypted by remember {
                            mutableStateOf<ByteArray?>(null)
                        }
                        var decrypted by remember {
                            mutableStateOf<ByteArray?>(null)
                        }
                        Button(
                            onClick = {
                                encrypted = Crypto.encrypt("Hello world!".encodeToByteArray())
                            }
                        ) {
                            Text("Encrypt")
                        }
                        encrypted?.let {
                            Text(it.decodeToString())
                        }
                        Button(
                            onClick = {
                                encrypted?.let {
                                    decrypted = Crypto.decrypt(it)
                                }
                            }
                        ) {
                            Text("Decrypt")
                        }
                        decrypted?.let {
                            Text(it.decodeToString())
                        }
                    }
                }
            }
        }
    }
}