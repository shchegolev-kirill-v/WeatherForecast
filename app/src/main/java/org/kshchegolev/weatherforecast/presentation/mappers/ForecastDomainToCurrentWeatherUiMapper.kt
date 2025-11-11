package org.kshchegolev.weatherforecast.presentation.mappers

import org.kshchegolev.weatherforecast.domain.models.Forecast
import org.kshchegolev.weatherforecast.presentation.models.CurrentWeatherUi

interface ForecastDomainToCurrentWeatherUiMapper {
    fun map(domain: Forecast): CurrentWeatherUi
}