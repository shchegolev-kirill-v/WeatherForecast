package org.kshchegolev.weatherforecast.domain.models

data class DailyForecast(
    val day: String,
    val tempMax: String,
    val tempMin: String,
    val iconUrl: String,
    val timestamp: Long
)