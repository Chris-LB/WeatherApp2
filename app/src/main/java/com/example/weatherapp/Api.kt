package com.example.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("weather")
    fun getCurrentConditions(
        @Query("zip") zip: String,
        @Query("units") units: String ="imperial",
        @Query("appid") appId: String = "86c3cd738208f35656c55a2d121e428d",
    ): Call<CurrentConditions>
}