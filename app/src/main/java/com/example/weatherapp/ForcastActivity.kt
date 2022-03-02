package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ActivityForcastBinding
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class ForcastActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    private lateinit var conditionIcon: ImageView

    @Inject
    lateinit var viewModel: ForecastViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forcast)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        viewModel.dailyForecast.observe(this) { dailyForecast ->
            recyclerView.adapter = ForecastAdapter(dailyForecast.forecastList)
        }
        viewModel.loadData()

    }
}