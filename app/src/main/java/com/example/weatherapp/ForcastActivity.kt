package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ForcastActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    private lateinit var api: DailyForecastApi
    private lateinit var conditionIcon: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forcast)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)



        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val retofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        api = retofit.create(DailyForecastApi::class.java)
    }

    override fun onResume() {
        super.onResume()
        val call: Call<DailyForecast> = api.getCurrentConditions("55055")
        call.enqueue(object : Callback<DailyForecast> {
            override fun onResponse(call: Call<DailyForecast>, response: Response<DailyForecast>) {
                val dailyForecast = response.body()
                dailyForecast?.let {
                    recyclerView.adapter = ForecastAdapter(dailyForecast.forecastList)
                }
            }

            override fun onFailure(call: Call<DailyForecast>, t: Throwable) {
            }

        })
    }
}