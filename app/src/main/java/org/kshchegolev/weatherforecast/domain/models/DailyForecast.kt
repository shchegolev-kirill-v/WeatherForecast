package org.kshchegolev.weatherforecast.domain.models

data class DailyForecast(
    val timestamp: Long?,
    val tempMax: Double?,
    val tempMin: Double?,
    val iconUrl: String?
)