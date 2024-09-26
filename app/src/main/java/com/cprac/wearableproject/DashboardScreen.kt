package com.cprac.wearableproject

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.activity.compose.BackHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer
import kotlin.concurrent.schedule

@Composable
fun DashboardScreen(navController: NavController, activity: ComponentActivity, userViewModel: UserViewModel) {
    var heartRate by remember { mutableIntStateOf(0) }
    var oxygenLevel by remember { mutableIntStateOf(0) }

    BackHandler {
        activity.finish()
    }

    LaunchedEffect(Unit) {
        fetchData { bpm, spo2 ->
            heartRate = bpm
            oxygenLevel = spo2
        }

        Timer("refreshTimer", true).schedule(0, 10000) {
            fetchData { bpm, spo2 ->
                heartRate = bpm
                oxygenLevel = spo2
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Welcome, ${userViewModel.name}!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Age: ${userViewModel.age}", style = MaterialTheme.typography.bodyLarge)
        Text("Height: ${userViewModel.height} cm", style = MaterialTheme.typography.bodyLarge)
        Text("Weight: ${userViewModel.weight} kg", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Heart Rate: $heartRate bpm", style = MaterialTheme.typography.bodyLarge)
        Text("Oxygen Level: $oxygenLevel %", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.weight(1f))
        BottomNavigationBar(navController)
    }
}

fun fetchData(onResult: (Int, Int) -> Unit) {
    val apiService = RetrofitClient.instance
    apiService.getSensorData().enqueue(object : Callback<SensorData> {
        override fun onResponse(call: Call<SensorData>, response: Response<SensorData>) {
            if (response.isSuccessful && response.body() != null) {
                val data = response.body()!!
                onResult(data.bpm, data.spo2)
            }
        }

        override fun onFailure(call: Call<SensorData>, t: Throwable) {
        }
    })
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
    ) {
        IconButton(onClick = {}) {
            Icon(Icons.Filled.AccountCircle, contentDescription = "Account")
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = {}) {
            Icon(Icons.Filled.Notifications, contentDescription = "Alerts")
        }
        IconButton(onClick = {}) {
            Icon(Icons.Filled.Home, contentDescription = "Dashboard")
        }
    }
}


