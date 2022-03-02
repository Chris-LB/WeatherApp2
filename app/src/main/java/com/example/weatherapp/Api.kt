package com.example.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("weather")
    suspend fun getCurrentConditions(
        @Query("zip") zip: String,
        @Query("units") units: String = "imperial",
        @Query("appid") appId: String = "86c3cd738208f35656c55a2d121e428d",
    ): CurrentConditions

    @GET("forecast/daily")
    suspend fun getForecast(
        @Query("zip") zip: String,
        @Query("units") unit: String = "imperial",
        @Query("appid") appId: String = "927421ea8053c52b201510fceed2dc23",
        @Query("cnt") count: Int = 16

    ): DailyForecast
}