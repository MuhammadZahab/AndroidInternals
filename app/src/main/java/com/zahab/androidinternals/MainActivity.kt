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

        lifecycleScope.launch {
            writeToExternalStorate()
        }

        setContent {
            AndroidInternalszahabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        var bitmap by remember {
                            mutableStateOf<Bitmap?>(null)
                        }

                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.OpenDocument()
                        ) { uri ->

                                uri?.let {
                                    lifecycleScope.launch {
                                        bitmap = readUriAsBitmap(it)
                                    }
                                }
                        }


                        Button(onClick = {
                            launcher.launch(arrayOf("image/*"))
                        }) {
                            Text("Pick Photo")
                        }

                        bitmap?.let {
                            Image(
                                bitmap = bitmap!!.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }


                }
            }
        }
    }

    private suspend fun readUriAsBitmap(uri: Uri): Bitmap? = withContext(Dispatchers.IO) {


        val bytes = contentResolver.openInputStream(uri)?.use { input ->
            input.readBytes()
        } ?: return@withContext null
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)


    }


    override fun onDestroy() {
        super.onDestroy()
        println("Receiver Unregistered")
    }

    private suspend fun writeToInternalStorage() = withContext(Dispatchers.IO) {

        val file = File(filesDir, "hello.txt")
        FileOutputStream(file).use { outputStream ->
            outputStream.write("Bismillah".toByteArray())
        }

    }

    private suspend fun writeToExternalStorate() = withContext(Dispatchers.IO) {
        val dir = getExternalFilesDir(null)
        val file = File(dir, "hello.txt")

        FileOutputStream(file).use { outputStream ->
            outputStream.write("Allah o Akbar".toByteArray())
        }
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

