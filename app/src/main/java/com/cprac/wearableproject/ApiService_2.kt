package com.cprac.wearableproject

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

// Data model for the request body
data class HealthMetricsRequest(
    val heart_rate: Int,
    val spo2: Int,
    val timestamp: String
)

// Data model for the response from the server
data class HealthMetricsResponse(
    val status: String,
    val heart_rate: Double,
    val spo2: Double,
    val timestamp: String,
    val predictions: Int,
    val reason: List<String>
)

// Retrofit API interface
interface HealthMetricsApi {
    @POST("https://wearable-server.onrender.com/update/")
    fun postHealthMetrics(@Body request: HealthMetricsRequest): Call<HealthMetricsResponse>
}

// Create Retrofit instance
fun createRetrofitService(): HealthMetricsApi {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://wearable-server.onrender.com/update/") // Replace with your base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(HealthMetricsApi::class.java)
}
