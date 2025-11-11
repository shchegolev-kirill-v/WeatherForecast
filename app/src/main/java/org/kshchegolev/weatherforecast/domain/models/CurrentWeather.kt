package org.kshchegolev.weatherforecast.domain.models

data class CurrentWeather(
    val temp: Double?,
    val iconUrl: String?,
    val updatedAt: Long
)