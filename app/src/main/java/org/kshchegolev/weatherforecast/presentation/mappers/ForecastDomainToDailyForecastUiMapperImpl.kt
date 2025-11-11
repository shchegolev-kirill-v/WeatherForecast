package org.kshchegolev.weatherforecast.presentation.mappers

import org.kshchegolev.weatherforecast.domain.models.Forecast
import org.kshchegolev.weatherforecast.presentation.helpers.Formatters.toCompleteUrlOrNull
import org.kshchegolev.weatherforecast.presentation.helpers.Formatters.toLocalDateFormattedOrEmpty
import org.kshchegolev.weatherforecast.presentation.helpers.Formatters.toTemperatureOrDefault
import org.kshchegolev.weatherforecast.presentation.models.DailyForecastUi
import javax.inject.Inject

class ForecastDomainToDailyForecastUiMapperImpl @Inject constructor() :
    ForecastDomainToDailyForecastUiMapper {
    override fun map(domain: Forecast): List<DailyForecastUi> =
        domain.dailyForecasts.map {
            DailyForecastUi(
                day = it.timestamp.toLocalDateFormattedOrEmpty(),
                tempMax = it.tempMax.toTemperatureOrDefault(),
                tempMin = it.tempMin.toTemperatureOrDefault(),
                iconUrl = it.iconUrl.toCompleteUrlOrNull()
            )
        }
}