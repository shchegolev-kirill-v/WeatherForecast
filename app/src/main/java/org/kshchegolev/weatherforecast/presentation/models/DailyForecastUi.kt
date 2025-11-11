package org.kshchegolev.weatherforecast.presentation.models

data class DailyForecastUi(
    val day: String,
    val tempMax: String,
    val tempMin: String,
    val iconUrl: String?
)