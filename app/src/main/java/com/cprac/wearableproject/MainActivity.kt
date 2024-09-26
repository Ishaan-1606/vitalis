package com.cprac.wearableproject


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.ViewModelProvider
import com.cprac.wearableproject.ui.theme.WearableProjectTheme

class MainActivity : ComponentActivity() {
    private lateinit var userViewModel: UserViewModel // Declare UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize UserViewModel
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        setContent {
            WearableProjectTheme {
                val navController = rememberNavController() // Initialize NavController
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationGraph(navController, this, Modifier.padding(innerPadding), userViewModel) // Pass ViewModel
                }
            }
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, activity: ComponentActivity, modifier: Modifier, userViewModel: UserViewModel) {
    NavHost(navController, startDestination = "splash", modifier = modifier) {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginPage(navController) }
        composable("user_form") { UserForm(navController, userViewModel) } // Pass ViewModel
        composable("dashboard") { DashboardScreen(navController, activity, userViewModel) } // Pass ViewModel
    }
}
