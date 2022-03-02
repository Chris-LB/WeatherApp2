package com.example.weatherapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.ActivityMainBinding
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
class MainActivity : AppCompatActivity() {
    private lateinit var blinding: ActivityMainBinding
    private var apiKey = "86c3cd738208f35656c55a2d121e428d"
    private lateinit var api: Api
    @Inject
    lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        blinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(blinding.root)

    }

    override fun onResume() {
        super.onResume()
        viewModel.currentConditions.observe(this) { currentConditions ->
            blindData(currentConditions)
        }
        viewModel.loadData()

    }

    private fun blindData(currentConditions: CurrentConditions) {
        blinding.cityName.text = currentConditions.name
        print(currentConditions.main.temp)
        blinding.temperature.text =
            getString(R.string.temperature, currentConditions.main.temp.toInt())
        blinding.feelsLike.text =
            "feels like " + getString(R.string.feels_like, currentConditions.main.feelsLike.toInt())
        blinding.High.text =
            "High " + getString(R.string.High, currentConditions.main.tempMax.toInt())
        blinding.low.text = "Low " + getString(R.string.low, currentConditions.main.tempMin.toInt())
        blinding.pressure.text = "Pressure " + getString(
            R.string.pressure,
            currentConditions.main.pressure.toInt()
        ) + " hPa"
        blinding.Humid.text =
            "Humidity" + getString(R.string.Humid, currentConditions.main.humidity.toInt())

        val iconName = currentConditions.weather.firstOrNull()?.icon
        val iconURL = "https://openweathermap.org/img/wn/${iconName}@2x.png"
        Glide.with(this)
            .load(iconURL)
            .into(blinding.conditionIcon)

    }

    fun openNextView(view: android.view.View) {
        val intent = Intent(this, ForcastActivity::class.java)
        startActivity(intent)
    }
}