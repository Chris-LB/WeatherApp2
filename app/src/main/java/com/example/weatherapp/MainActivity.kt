package com.example.weatherapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MainActivity : AppCompatActivity() {
    private lateinit var conditionIcon: ImageView
    private var apiKey = "86c3cd738208f35656c55a2d121e428d"
    private lateinit var api: Api
    private lateinit var cityName: TextView
    private lateinit var currentTemp: TextView
    private lateinit var currentImage: ImageView
    private lateinit var maxTemp:TextView
    private lateinit var minTemp:TextView
    private lateinit var pressure:TextView
    private lateinit var humidity: TextView
    private lateinit var feelslike:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        conditionIcon = findViewById(R.id.condition_icon)
        cityName = findViewById(R.id.city_name)
        currentTemp = findViewById(R.id.temperature)
        currentImage = findViewById(R.id.condition_icon)
        maxTemp = findViewById(R.id.High)
        minTemp = findViewById(R.id.low)
        pressure = findViewById(R.id.pressure)
        humidity = findViewById(R.id.Humid)
        feelslike = findViewById(R.id.feels_like)



        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val retofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        api = retofit.create(Api::class.java)
    }
    override fun onResume(){
        super.onResume()
        val call:Call<CurrentConditions> = api.getCurrentConditions("55055")
        call.enqueue(object : Callback<CurrentConditions> {
            override fun onResponse(
                call: Call<CurrentConditions>,
                response: Response<CurrentConditions>
            ) {
                val currentConditions = response.body()
                currentConditions?.let {
                    blindData(it)
                }
            }

            override fun onFailure(call: Call<CurrentConditions>, t: Throwable) {

            }

        })
    }
    private fun blindData(currentConditions: CurrentConditions){
        cityName.text = currentConditions.name
        print(currentConditions.main.temp)
        currentTemp.text = getString(R.string.temperature, currentConditions.main.temp.toInt())
        feelslike.text = "feels like "+getString(R.string.feels_like,currentConditions.main.feelsLike.toInt())
        maxTemp.text = "High "+getString(R.string.High,currentConditions.main.tempMax.toInt())
        minTemp.text = "Low "+getString(R.string.low,currentConditions.main.tempMin.toInt())
        pressure.text = "Pressure "+getString(R.string.pressure,currentConditions.main.pressure.toInt())+" hPa"
        humidity.text = "Humidity"+getString(R.string.Humid, currentConditions.main.humidity.toInt())

        val iconName = currentConditions.weather.firstOrNull()?.icon
        val iconURL = "https://openweathermap.org/img/wn/${iconName}@2x.png"
        Glide.with(this)
            .load(iconURL)
            .into(currentImage)

    }

    fun openNextView(view: android.view.View) {
        val intent = Intent(this, ForcastActivity::class.java)
        startActivity(intent)
    }
}