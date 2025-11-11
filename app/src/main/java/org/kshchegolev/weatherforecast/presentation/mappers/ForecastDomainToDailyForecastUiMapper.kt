package org.kshchegolev.weatherforecast.presentation.mappers

import org.kshchegolev.weatherforecast.domain.models.Forecast
import org.kshchegolev.weatherforecast.presentation.models.DailyForecastUi

interface ForecastDomainToDailyForecastUiMapper {
    fun map(domain: Forecast): List<DailyForecastUi>
}