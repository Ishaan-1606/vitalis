package com.cprac.wearableproject

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.activity.compose.BackHandler
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer
import kotlin.concurrent.schedule
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import kotlin.math.pow

@Composable
fun DashboardScreen(navController: NavController, activity: ComponentActivity, userViewModel: UserViewModel) {
    var heartRate by remember { mutableIntStateOf(0) }
    var oxygenLevel by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
    val userName = remember { sharedPreferences.getString("userName", "User") ?: "User" }
    val userAge = remember { sharedPreferences.getString("userAge", "N/A") ?: "N/A" }
    val userHeight = remember { sharedPreferences.getString("userHeight", "N/A")?.toDoubleOrNull() ?: 0.0 }
    val userWeight = remember { sharedPreferences.getString("userWeight", "N/A")?.toDoubleOrNull() ?: 0.0 }

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

    // BMI Calculation
    val bmi = if (userHeight > 0) userWeight / (userHeight / 100.0).pow(2) else 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Welcome Text
                Text(
                    "Welcome, $userName!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            item {
                // User Information Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("User Information", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        UserDetailRow("Age:", "$userAge years")
                        UserDetailRow("Height:", "$userHeight cm")
                        UserDetailRow("Weight:", "$userWeight kg")
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Health Metrics", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        HealthMetrics(oxygenLevel, heartRate)
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("BMI", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Your BMI is: %.2f".format(bmi), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)


                        Button(
                            onClick = { navController.navigate("BMI_INFO/$bmi") }, // Navigate to BMI_INFO screen with bmi as a parameter
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Tap to learn more", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

        }
        BottomNavigationBar(navController)
    }
}
@Composable
fun UserDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label,fontWeight = FontWeight.Bold)
        Text(value, fontWeight = FontWeight.Bold)
    }
}
@Composable
fun HealthMetrics(oxygenLevel: Int, heartRate: Int) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(150.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                clip = true
            ),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HealthMetricBox(
                iconRes = R.drawable.spo2_icon,
                value = oxygenLevel.toString(),
                label = "SpO2\n(%)"
            )
            HealthMetricBox(
                iconRes = R.drawable.ic_heart,
                value = heartRate.toString(),
                label = "Heart Rate\n(BPM)"
            )
        }
    }
}
@Composable
fun HealthMetricBox(iconRes: Int, value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.width(120.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = value,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp
        )
    }
}
@Composable
fun BottomNavigationBar(navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                navController.navigate("dashboard")
            }) {
                Icon(Icons.Filled.Home, contentDescription = "Dashboard")
            }
            IconButton(onClick = {
                navController.navigate("checkAnomalies")
            }) {
                Icon(Icons.Filled.Notifications, contentDescription = "Alerts")
            }
            IconButton(onClick = {
                navController.navigate("ChatBot")
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.gemini_icon),
                    contentDescription = "Gemini Icon",
                    modifier = Modifier.scale(0.6f)
                )
            }
            IconButton(onClick = {
                navController.navigate("updateUserDetails")
            }) {
                Icon(Icons.Filled.AccountCircle, contentDescription = "Account")
            }
        }
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
