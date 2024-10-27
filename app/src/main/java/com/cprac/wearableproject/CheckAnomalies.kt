package com.cprac.wearableproject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer
import kotlin.concurrent.schedule

@Composable
fun CheckAnomalies(navController: NavController) {
    var heartRate by remember { mutableIntStateOf(0) }
    var oxygenLevel by remember { mutableIntStateOf(0) }
    var reasons by remember { mutableStateOf(emptyList<String>()) }


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
        delay(1000)
        Timer("refreshTimer", true).schedule(0, 10000) {
            fetchData2(heartRate, oxygenLevel) { bpm, spo2, anomalyReasons ->
                heartRate = bpm
                oxygenLevel = spo2
                reasons = anomalyReasons
            }
        }

    }
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ){ innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                HealthMetrics(oxygenLevel, heartRate) // Display health metrics
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Anomalies(reasons = reasons) // Display anomalies if any
            }
        }
    }



}

@Composable
fun Anomalies(reasons: List<String>) {
    val reasonMap = hashMapOf<String, String>(
        "High HR" to "Indicates the heart is beating faster than normal due to stress, exertion, or health issues.",
        "Low HR" to "Suggests a slower heartbeat, which can indicate good fitness or potential bradycardia.",
        "Low spO2" to "Indicates insufficient oxygen in the bloodstream, potentially due to respiratory issues.",
        "No anomaly" to "Heart rate and oxygen levels are normal, indicating good cardiovascular and respiratory health."
    )

    val titleMap=hashMapOf<String,String>(
        "High HR" to "High Heart Rate",
        "Low HR" to "Low Heart Rate",
        "Low spO2" to "Low Blood Oxygen",
        "No anomaly" to "No Anomalies"
    )

    if (reasons.isNotEmpty()) {
        Card(
            modifier = Modifier
                .width(300.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp),
                    clip = true
                ),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                for (reason in reasons) {
                    Text(
                        text = titleMap.getOrDefault(reason, "Unknown anomaly"),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = reasonMap.getOrDefault(reason, "Unknown anomaly"),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center

                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

            }
        }
    }
}

fun fetchData2(
    heartRate: Int,
    spo2: Int,
    onResult: (Int, Int, List<String>) -> Unit
) {
    val request = HealthMetricsRequest(heart_rate = heartRate, spo2 = spo2, timestamp = System.currentTimeMillis().toString())
    val api = createRetrofitService()

    api.postHealthMetrics(request).enqueue(object : Callback<HealthMetricsResponse> {
        override fun onResponse(
            call: Call<HealthMetricsResponse>,
            response: Response<HealthMetricsResponse>
        ) {
            if (response.isSuccessful) {
                val data = response.body()
                data?.let {
                    onResult(it.heart_rate.toInt(), it.spo2.toInt(), it.reason)
                }
            } else {
                // Handle error response
                onResult(0, 0, listOf("Failed to fetch data: ${response.message()}"))
            }
        }

        override fun onFailure(call: Call<HealthMetricsResponse>, t: Throwable) {
            // Handle error
            onResult(0, 0, listOf("Error: ${t.message}"))
        }
    })
}
