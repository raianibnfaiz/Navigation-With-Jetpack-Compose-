package com.example.basicjetpackcomposeproject

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.basicjetpackcomposeproject.ui.theme.BasicJetpackComposeProjectTheme
import com.example.basicjetpackcomposeproject.ui.theme.HomeScreen

sealed class Destination(val route: String){
    object Home: Destination("home")
    object Profile: Destination("profile")
    object List: Destination("list")
    object Detail: Destination("detail/{elementId}")
    fun createRoute(id:Int) = "detail/${id}"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicJetpackComposeProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavigationAppHost(navController = navController)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
@Composable
fun NavigationAppHost(navController: NavHostController){
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = "home"){
        composable(Destination.Home.route){ HomeScreen(navController)}
        composable(Destination.Profile.route){ ProfileScreen()}
        composable(Destination.List.route){ ListScreen(navController)}
        composable(Destination.Detail.route){navBackStackEntry ->
            val elementId = navBackStackEntry.arguments?.getString("elementId")
            if(elementId == null){
                Toast.makeText(context, "Element id is required", Toast.LENGTH_SHORT).show()
            }
            else{
                DetailScreen(elementId = elementId.toInt())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicJetpackComposeProjectTheme {
        Greeting("Android")
    }
}