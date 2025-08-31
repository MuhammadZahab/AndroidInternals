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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.zahab.androidinternals.ui.theme.AndroidInternalszahabTheme
import kotlinx.coroutines.flow.StateFlow
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
                    val counterViewModel = viewModel<CounterViewModel>()
                    val counter by counterViewModel.counter.collectAsState()

                    NavHost(
                        navController = navController,
                        startDestination = ScreenA,
                        modifier = Modifier.padding(_innerPadding)
                    ) {
                        composable<ScreenA> {

                            Screen(
                                text = "Go To Screen B",
                                route = ScreenBAndCGraph,
                                content = {
                                    Content(
                                        screenName = ScreenA.toString(),
                                        counter = counter,
                                        onIncrementClick = counterViewModel::increment,
                                    )
                                },
                                navController = navController
                            )
                        }


                        navigation<ScreenBAndCGraph>(
                            startDestination = ScreenB,
                        ) {

                            composable<ScreenB> {



                                Screen(
                                    text = "Go To Screen C",
                                    route = ScreenC,
                                    content = {
                                        Content(
                                            screenName = ScreenB.toString(),
                                            counter = counter,
                                            onIncrementClick = counterViewModel::increment,
                                        )
                                    },
                                    navController = navController
                                )


                            }

                            composable<ScreenC> {


                                Content(
                                    screenName = ScreenC.toString(),
                                    counter = counter,
                                    onIncrementClick = counterViewModel::increment,
                                )
                            }


                        }
                    }
                }
            }
        }
    }
}

@Composable
private inline fun <reified VM : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController
): VM {

    val parentRoute = destination.parent?.route
        ?: error("sharedViewModel() called but no parent route found. Are you inside a nested graph?")

    val parentEntry = remember(this) {
        navController.getBackStackEntry(route = parentRoute)
    }

    return viewModel(viewModelStoreOwner = parentEntry)
}


@Composable
fun Screen(
    text: String,
    route: Any,
    content: @Composable () -> Unit,
    navController: NavController,
) {
    Column(modifier = Modifier.background(color = Color.White)) {
        Box(modifier = Modifier.weight(1f)) {
            content()
        }
        Button(
            onClick = {
                navController.navigate(route)
            },
            modifier = Modifier.padding(16.dp)

        ) {
            Text(
                text = text
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

    Screen(
        text = "Go To Specific Screen",
        route = Unit,
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

@Serializable
data object ScreenC

@Serializable
data object ScreenBAndCGraph