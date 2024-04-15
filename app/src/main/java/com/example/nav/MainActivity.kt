package com.example.nav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nav.ui.theme.NavTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavTheme {
                NavigationGraph()
            }
        }
    }
}


@Composable
fun NavigationGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screens.First) {
        composable(Screens.First) {
            FirstScreen(it, navController)
        }
        composable(
            "${Screens.Second}/{userId}", arguments =
            listOf(navArgument("userId") {
                type = NavType.StringType
            })
        ) {
            SecondScreen(navController, it)
        }
        composable(Screens.Third) {
            ThirdScreen()
        }
    }
}


@Composable
fun FirstScreen(
    it: NavBackStackEntry,
    navController: NavHostController
) {
    val text = it.savedStateHandle.get<String>("text1")
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Red)
            .clickable {
                val x= "user id is 1112233"
                navController.navigate("${Screens.Second}/$x")
            }
    ) {
        Text(text = Screens.First.plus(text.orEmpty()))
    }
}



@Composable
fun SecondScreen(navController: NavController, it: NavBackStackEntry)  {
    navController.previousBackStackEntry?.savedStateHandle?.set(
        "text1",
        " test text value "
    )
    val receivedArgument = it.arguments?.getString("userId")
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Yellow)
            .clickable {
                navController.navigate(Screens.Third)
            }
    ) {
        Text(text = Screens.Second.plus(receivedArgument))
    }
}



@Composable
fun ThirdScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Green)
    ) {
        Text(text = Screens.Third)
    }
}
