package org.kshchegolev.weatherforecast.data.mappers

import org.kshchegolev.weatherforecast.domain.models.CurrentWeather
import org.kshchegolev.weatherforecast.domain.models.DailyForecast
import org.kshchegolev.weatherforecast.domain.models.Forecast
import org.kshchegolev.weatherforecast.domain.models.HourlyForecast
import org.kshchegolev.weatherforecast.network.models.ForecastResponse
import java.time.Instant
import javax.inject.Inject

class ForecastResponseDtoToDomainMapperImpl @Inject constructor() :
    ForecastResponseDtoToDomainMapper {

    override fun map(dto: ForecastResponse): Forecast =
        Forecast(
            locationName = dto.location?.name,
            currentWeather = CurrentWeather(
                temp = dto.current?.temp,
                iconUrl = dto.current?.condition?.iconUrl,
                updatedAt = Instant.now().epochSecond
            ),
            hourlyForecasts = dto.forecast?.forecastDays?.firstOrNull()?.hours?.map {
                HourlyForecast(
                    temp = it.temp,
                    iconUrl = it.condition?.iconUrl,
                    timestamp = it.timestamp
                )
            }.orEmpty(),
            dailyForecasts = dto.forecast?.forecastDays?.map {
                DailyForecast(
                    timestamp = it.timestamp,
                    tempMax = it.day?.maxTemp,
                    tempMin = it.day?.minTemp,
                    iconUrl = it.day?.condition?.iconUrl
                )
            }.orEmpty()
        )
}