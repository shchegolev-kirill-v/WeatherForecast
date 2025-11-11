package org.kshchegolev.weatherforecast.presentation.mappers

import org.kshchegolev.weatherforecast.domain.models.Forecast
import org.kshchegolev.weatherforecast.presentation.models.HourlyForecastUi

interface ForecastDomainToHourlyForecastUiMapper {
    fun map(domain: Forecast): List<HourlyForecastUi>
}