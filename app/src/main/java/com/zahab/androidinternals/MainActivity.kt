package com.zahab.androidinternals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zahab.androidinternals.ui.theme.AndroidInternalszahabTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            AndroidInternalszahabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _innerPadding ->
                    val navController = rememberNavController()


//                    We can also use this, just added for learning
//                    val navGraph = remember(navController) {
//                        navController.createGraph(startDestination = ScreenA) {
//                            composable<ScreenA> {}
//                            composable<ScreenB> {}
//                        }
//                    }
//
//                    NavHost(
//                        navController = navController,
//                        graph = navGraph
//                    )


                    NavHost(
                        navController = navController,
                        startDestination = ScreenA,
                        modifier = Modifier.padding(_innerPadding)
                    ) {
                        composable<ScreenA> {
                            val counterViewModel = viewModel<CounterViewModel>()

                            ScreenA(
                                content = {
                                    Content(
                                        screenName = ScreenA.toString(),
                                        counter = counterViewModel.counter,
                                        onIncrementClick = counterViewModel::increment,
                                    )
                                },
                                navController = navController
                            )
                        }
                        composable<ScreenB> {
                            val counterViewModel = viewModel<CounterViewModel>()

                            Content(
                                screenName = ScreenB.toString(),
                                counter = counterViewModel.counter,
                                onIncrementClick = counterViewModel::increment,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenA(
    content: @Composable () -> Unit,
    navController: NavController,
) {
    Column(modifier = Modifier.background(color = Color.White)) {
        Box(modifier = Modifier.weight(1f)) {
            content()
        }
        Button(
            onClick = {
                navController.navigate(ScreenB)
            },
            modifier = Modifier.padding(16.dp)

        ) {
            Text(
                text = "Go to Screen B"
            )
        }
    }
}


@Composable
fun Content(
    screenName: String,
    counter: Int,
    onIncrementClick: () -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {


        Text(
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .fillMaxWidth()
                .padding(20.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            text = screenName
        )

        Button(
            onClick = onIncrementClick,
        ) {
            Text(
                modifier = Modifier.wrapContentSize(),
                text = "Counter = $counter"
            )
        }
    }
}


@Composable
@Preview(showBackground = true)

fun ScreenAPreview() {
    val navController = rememberNavController()

    ScreenA(
        content = {
            Content("ScreenA", 5, {})

        },
        navController = navController
    )

}

@Composable
@Preview(showBackground = true)
fun ContentPreview() {
    Content("ScreenA", 5, {})
}

@Serializable
data object ScreenA

@Serializable
data object ScreenB