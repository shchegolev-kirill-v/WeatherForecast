package org.kshchegolev.weatherforecast.presentation.models

import org.kshchegolev.weatherforecast.domain.models.CurrentWeather
import org.kshchegolev.weatherforecast.domain.models.DailyForecast
import org.kshchegolev.weatherforecast.domain.models.HourlyForecast

data class UiState(
    val isLoading: Boolean,
    val isRefreshing: Boolean,
    val isError: Boolean,
    val shouldShowErrorSnackBar: Boolean,
    val title: String,
    val currentWeather: CurrentWeather,
    val hourlyForecasts: List<HourlyForecast>,
    val dailyForecasts: List<DailyForecast>
) {

    companion object {
        val EMPTY =
            UiState(
                isLoading = false,
                isRefreshing = false,
                isError = false,
                shouldShowErrorSnackBar = false,
                title = "",
                currentWeather = CurrentWeather(
                    temp = "",
                    iconUrl = "",
                    updatedAt = ""
                ),
                hourlyForecasts = emptyList(),
                dailyForecasts = emptyList()
            )
    }
}