package org.kshchegolev.weatherforecast.data.mappers

import org.kshchegolev.weatherforecast.domain.models.Forecast
import org.kshchegolev.weatherforecast.network.models.ForecastResponse

interface ForecastResponseDtoToDomainMapper {
    fun map(dto: ForecastResponse): Forecast
}