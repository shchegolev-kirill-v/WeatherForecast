package org.kshchegolev.weatherforecast.domain.models

data class Forecast(
    val locationName: String?,
    val currentWeather: CurrentWeather,
    val hourlyForecasts: List<HourlyForecast>,
    val dailyForecasts: List<DailyForecast>
)