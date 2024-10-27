package com.cprac.wearableproject

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("https://wearable-dummy-data.onrender.com/")
    fun getSensorData(): Call<SensorData>
}
