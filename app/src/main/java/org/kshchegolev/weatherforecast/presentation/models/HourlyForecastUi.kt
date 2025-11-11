package org.kshchegolev.weatherforecast.presentation.models

data class HourlyForecastUi(
    val hour: String,
    val temp: String,
    val iconUrl: String?,
    val timestamp: Long?
)