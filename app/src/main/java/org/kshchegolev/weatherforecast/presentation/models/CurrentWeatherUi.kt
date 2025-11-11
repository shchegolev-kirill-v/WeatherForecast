package org.kshchegolev.weatherforecast.presentation.models

data class CurrentWeatherUi(
    val temp: String,
    val iconUrl: String?,
    val updatedAt: String
)