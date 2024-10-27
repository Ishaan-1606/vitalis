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
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

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
        composable("ChatBot"){ChatBot(ChatBotVM(),navController)}
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginPage(navController) }
        composable("user_form") { UserForm(navController, userViewModel) }
        composable("dashboard") { DashboardScreen(navController, activity, userViewModel) }
        composable("updateUserDetails") { UpdateDetails(navController, userViewModel) }
        composable("BMI_INFO/{bmi}") { backStackEntry ->
            val bmi = backStackEntry.arguments?.getString("bmi")?.toFloatOrNull() ?: 0f
            BMIInfoScreen(navController, bmi)}
        composable("checkAnomalies") { CheckAnomalies(navController)}
    }
}

