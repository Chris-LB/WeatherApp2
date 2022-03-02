package com.example.weatherapp


import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.ActivityForcastBinding
import com.example.weatherapp.databinding.ForcastDataBinding
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ForecastAdapter(private val data: List<DayForecast>) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    class ViewHolder(private val blinding: ForcastDataBinding) :
        RecyclerView.ViewHolder(blinding.root) {


        @RequiresApi(Build.VERSION_CODES.O)
        fun blind(dayForcast: DayForecast) {
            val dateInstance = Instant.ofEpochSecond(dayForcast.date)
            val dateTime = LocalDateTime.ofInstant(dateInstance, ZoneId.systemDefault())
            val sunriseInstance = Instant.ofEpochSecond(dayForcast.sunrise)
            val sunriseTime = LocalDateTime.ofInstant(sunriseInstance, ZoneId.systemDefault())
            val sunsetInstance = Instant.ofEpochSecond(dayForcast.sunset)
            val sunsetTime = LocalDateTime.ofInstant(sunsetInstance, ZoneId.systemDefault())
            val dateFormat = DateTimeFormatter.ofPattern("MMM dd")
            val timeFormat = DateTimeFormatter.ofPattern("h:mma")


            blinding.date.text = dateFormat.format(dateTime)
            blinding.sunrise.text =
                itemView.context.getString(R.string.Sunrise, timeFormat.format(sunriseTime))
            blinding.sunset.text =
                itemView.context.getString(R.string.Sunset, timeFormat.format(sunsetTime))
            blinding.temp.text = itemView.context.getString(R.string.Day, dayForcast.temp.day)
            blinding.max.text = itemView.context.getString(R.string.Maxtemp, dayForcast.temp.max)
            blinding.min.text = itemView.context.getString(R.string.Mintemp, dayForcast.temp.min)

            val iconName = dayForcast.weather.firstOrNull()?.icon
            val iconURL = "https://openweathermap.org/img/wn/${iconName}@2x.png"
            Glide.with(itemView)
                .load(iconURL)
                .into(blinding.forcastImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val blinding: ForcastDataBinding = ForcastDataBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(blinding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.blind(data[position])
    }

    override fun getItemCount() = data.size
}