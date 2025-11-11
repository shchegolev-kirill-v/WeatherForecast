package org.kshchegolev.weatherforecast.presentation

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.google.android.material.R.style
import com.google.android.material.textview.MaterialTextView
import org.kshchegolev.weatherforecast.R
import org.kshchegolev.weatherforecast.presentation.models.HourlyForecastUi
import org.kshchegolev.weatherforecast.presentation.views.dsl.dp
import org.kshchegolev.weatherforecast.presentation.views.dsl.imageView
import org.kshchegolev.weatherforecast.presentation.views.dsl.size
import org.kshchegolev.weatherforecast.presentation.views.dsl.textView
import org.kshchegolev.weatherforecast.presentation.views.dsl.wrapContentHeight
import org.kshchegolev.weatherforecast.presentation.views.dsl.wrapContentWidth

internal class HourlyForecastAdapter : ListAdapter<HourlyForecastUi, HourlyForecastVH>(HourlyForecastDiff) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HourlyForecastVH = HourlyForecastVH(itemView = createItemView(parent.context))

    override fun onBindViewHolder(
        holder: HourlyForecastVH,
        position: Int
    ) {
        holder.bindData(getItem(position))
    }

    private object HourlyForecastDiff : DiffUtil.ItemCallback<HourlyForecastUi>() {

        override fun areItemsTheSame(oldItem: HourlyForecastUi, newItem: HourlyForecastUi) =
            oldItem.timestamp == newItem.timestamp

        override fun areContentsTheSame(oldItem: HourlyForecastUi, newItem: HourlyForecastUi) =
            oldItem == newItem
    }

    private fun createItemView(context: Context): View {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(12.dp, 12.dp, 16.dp, 12.dp)

            textView {
                id = R.id.hourly_forecast_hour_textview
                wrapContentWidth()
                wrapContentHeight()
                setTextAppearance(style.TextAppearance_MaterialComponents_Subtitle1)
                setTextColor(context.getColor(R.color.md_theme_secondary))
            }

            imageView {
                id = R.id.hourly_forecast_icon_imageview
                setPadding(0.dp, 8.dp, 0.dp, 8.dp)
                size(56.dp, 56.dp)
            }

            textView {
                id = R.id.hourly_forecast_temp_textview
                wrapContentWidth()
                wrapContentHeight()
                setTextAppearance(style.TextAppearance_Material3_HeadlineMedium)
                setTextColor(context.getColor(R.color.md_theme_primary))
            }
        }
    }
}

class HourlyForecastVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val hourTextView: MaterialTextView = itemView.findViewById(R.id.hourly_forecast_hour_textview)
    val iconImageView: ImageView = itemView.findViewById(R.id.hourly_forecast_icon_imageview)
    val tempTextView: MaterialTextView = itemView.findViewById(R.id.hourly_forecast_temp_textview)

    fun bindData(hourlyForecast: HourlyForecastUi) {

        hourlyForecast.apply {
            hourTextView.text = hourlyForecast.hour
            iconImageView.load(hourlyForecast.iconUrl)
            tempTextView.text = hourlyForecast.temp
        }
    }
}

