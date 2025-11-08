package org.kshchegolev.weatherforecast.domain.models

data class HourlyForecast(
    val hour: String,
    val temp: String,
    val timestamp: Long
)