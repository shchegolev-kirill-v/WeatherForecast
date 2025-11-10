package org.kshchegolev.weatherforecast.presentation.views.components.screenpanels

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import org.kshchegolev.weatherforecast.domain.models.CurrentWeather
import org.kshchegolev.weatherforecast.domain.models.DailyForecast
import org.kshchegolev.weatherforecast.domain.models.HourlyForecast
import org.kshchegolev.weatherforecast.presentation.models.Panel
import org.kshchegolev.weatherforecast.presentation.views.components.cards.currentWeatherCard
import org.kshchegolev.weatherforecast.presentation.views.components.cards.dailyForecastCard
import org.kshchegolev.weatherforecast.presentation.views.components.cards.hourlyForecastCard
import org.kshchegolev.weatherforecast.presentation.views.dsl.bind
import org.kshchegolev.weatherforecast.presentation.views.dsl.dp
import org.kshchegolev.weatherforecast.presentation.views.dsl.size
import org.kshchegolev.weatherforecast.presentation.views.dsl.space
import org.kshchegolev.weatherforecast.presentation.views.dsl.verticalLayout

internal fun ViewGroup.contentPanel(
    showPanel: LiveData<Panel>,
    currentWeather: LiveData<CurrentWeather>,
    hourlyForecast: LiveData<List<HourlyForecast>>,
    dailyForecast: LiveData<List<DailyForecast>>
) =
    verticalLayout {
        bind(showPanel) {
            visibility = if (it == Panel.Content)
                View.VISIBLE
            else
                View.GONE
        }
        currentWeatherCard(currentWeather)
        space { size(0.dp, 24.dp) }
        hourlyForecastCard(hourlyForecast)
        space { size(0.dp, 24.dp) }
        dailyForecastCard(dailyForecast)
    }