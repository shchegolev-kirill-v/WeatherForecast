package org.kshchegolev.weatherforecast.domain.models

data class HourlyForecast(
    val temp: Double?,
    val iconUrl: String?,
    val timestamp: Long?
)