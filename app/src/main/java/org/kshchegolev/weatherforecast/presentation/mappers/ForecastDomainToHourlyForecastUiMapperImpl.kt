package org.kshchegolev.weatherforecast.presentation.mappers

import org.kshchegolev.weatherforecast.domain.models.Forecast
import org.kshchegolev.weatherforecast.presentation.helpers.Formatters.toCompleteUrlOrNull
import org.kshchegolev.weatherforecast.presentation.helpers.Formatters.toLocalTimeFormattedOrEmpty
import org.kshchegolev.weatherforecast.presentation.helpers.Formatters.toTemperatureOrDefault
import org.kshchegolev.weatherforecast.presentation.models.HourlyForecastUi
import javax.inject.Inject

class ForecastDomainToHourlyForecastUiMapperImpl @Inject constructor() :
    ForecastDomainToHourlyForecastUiMapper {
    override fun map(domain: Forecast): List<HourlyForecastUi> =
        domain.hourlyForecasts.map {
            HourlyForecastUi(
                hour = it.timestamp.toLocalTimeFormattedOrEmpty(),
                temp = it.temp.toTemperatureOrDefault(),
                iconUrl = it.iconUrl.toCompleteUrlOrNull(),
                timestamp = it.timestamp
            )
        }
}