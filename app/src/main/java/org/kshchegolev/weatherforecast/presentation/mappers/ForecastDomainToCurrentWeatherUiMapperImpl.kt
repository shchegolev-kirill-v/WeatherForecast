package org.kshchegolev.weatherforecast.presentation.mappers

import org.kshchegolev.weatherforecast.domain.models.Forecast
import org.kshchegolev.weatherforecast.presentation.helpers.Formatters.toCompleteUrlOrNull
import org.kshchegolev.weatherforecast.presentation.helpers.Formatters.toLocalTimeFormattedOrEmpty
import org.kshchegolev.weatherforecast.presentation.helpers.Formatters.toTemperatureOrDefault
import org.kshchegolev.weatherforecast.presentation.models.CurrentWeatherUi
import javax.inject.Inject

class ForecastDomainToCurrentWeatherUiMapperImpl @Inject constructor() :
    ForecastDomainToCurrentWeatherUiMapper {
    override fun map(domain: Forecast): CurrentWeatherUi =
        domain.currentWeather.run {
            CurrentWeatherUi(
                temp = temp.toTemperatureOrDefault(),
                iconUrl = iconUrl.toCompleteUrlOrNull(),
                updatedAt = updatedAt.toLocalTimeFormattedOrEmpty()
            )
        }
}
