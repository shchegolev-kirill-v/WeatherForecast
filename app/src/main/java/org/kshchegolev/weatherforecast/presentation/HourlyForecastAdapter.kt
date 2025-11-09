package org.kshchegolev.weatherforecast.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import coil3.load
import org.kshchegolev.weatherforecast.R
import org.kshchegolev.weatherforecast.domain.models.HourlyForecast

class HourlyForecastAdapter : ListAdapter<HourlyForecast, HourlyForecastVH>(HourlyForecastDiff) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HourlyForecastVH = HourlyForecastVH(
        itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.hourly_forecast_item, parent, false)
    )

    override fun onBindViewHolder(
        holder: HourlyForecastVH,
        position: Int
    ) {
        holder.bindData(getItem(position))
    }

    private object HourlyForecastDiff : DiffUtil.ItemCallback<HourlyForecast>() {

        override fun areItemsTheSame(oldItem: HourlyForecast, newItem: HourlyForecast) =
            oldItem.timestamp == newItem.timestamp

        override fun areContentsTheSame(oldItem: HourlyForecast, newItem: HourlyForecast) =
            oldItem == newItem
    }
}

class HourlyForecastVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val hourTextView: MaterialTextView = itemView.findViewById(R.id.hourly_forecast_hour_textview)
    val iconImageView: ImageView = itemView.findViewById(R.id.hourly_forecast_icon_imageview)
    val tempTextView: MaterialTextView = itemView.findViewById(R.id.hourly_forecast_temp_textview)

    fun bindData(hourlyForecast: HourlyForecast) {

        hourlyForecast.apply {
            hourTextView.text = hourlyForecast.hour
            iconImageView.load("https://cdn.weatherapi.com/weather/64x64/day/122.png")
            tempTextView.text = hourlyForecast.temp
        }
    }

}

