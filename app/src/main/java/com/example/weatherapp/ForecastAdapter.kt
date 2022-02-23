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
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ForecastAdapter(private val data: List<DayForecast>) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>(){

    class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val date: TextView = view.findViewById(R.id.date)
        val sunrise: TextView = view.findViewById(R.id.sunrise)
        val sunset: TextView = view.findViewById(R.id.sunset)
        val day: TextView = view.findViewById(R.id.temp)
        val maxTemp: TextView = view.findViewById(R.id.max)
        val minTemp: TextView = view.findViewById(R.id.min)
        val image: ImageView = view.findViewById(R.id.forcastImage)

        @RequiresApi(Build.VERSION_CODES.O)
        fun blind(dayForcast: DayForecast){
            val dateInstance = Instant.ofEpochSecond(dayForcast.date)
            val dateTime = LocalDateTime.ofInstant(dateInstance, ZoneId.systemDefault())
            val sunriseInstance = Instant.ofEpochSecond(dayForcast.sunrise)
            val sunriseTime = LocalDateTime.ofInstant(sunriseInstance,ZoneId.systemDefault())
            val sunsetInstance = Instant.ofEpochSecond(dayForcast.sunset)
            val sunsetTime = LocalDateTime.ofInstant(sunsetInstance,ZoneId.systemDefault())
            val dateFormat = DateTimeFormatter.ofPattern("MMM dd")
            val timeFormat = DateTimeFormatter.ofPattern("h:mma")


            date.text = dateFormat.format(dateTime)
            sunrise.text = itemView.context.getString(R.string.Sunrise,timeFormat.format(sunriseTime))
            sunset.text = itemView.context.getString(R.string.Sunset,timeFormat.format(sunsetTime))
            day.text = itemView.context.getString(R.string.Day, dayForcast.temp.day)
            maxTemp.text = itemView.context.getString(R.string.Maxtemp, dayForcast.temp.max)
            minTemp.text = itemView.context.getString(R.string.Mintemp, dayForcast.temp.min)

            val iconName = dayForcast.weather.firstOrNull()?.icon
            val iconURL = "https://openweathermap.org/img/wn/${iconName}@2x.png"
            Glide.with(itemView)
                .load(iconURL)
                .into(image)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.forcast_data,parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.blind(data[position])
    }

    override fun getItemCount() = data.size
}