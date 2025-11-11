package org.kshchegolev.weatherforecast.data

import org.kshchegolev.weatherforecast.data.mappers.ForecastResponseDtoToDomainMapper
import org.kshchegolev.weatherforecast.domain.Result
import org.kshchegolev.weatherforecast.domain.models.Forecast
import org.kshchegolev.weatherforecast.network.ForecastApi
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val forecastApi: ForecastApi,
    private val forecastResponseDtoToDomainMapper: ForecastResponseDtoToDomainMapper
) : ForecastRepository {

    override suspend fun getForecast(location: String, days: Int): Result<Forecast> {
        val result = forecastApi.getForecast(location= location, days = days)
        when (result) {
            is Result.Success -> {
                val forecast = forecastResponseDtoToDomainMapper.map(result.data)
                return Result.Success(forecast)
            }

            is Result.Failure -> {
                return Result.Failure(result.message)
            }
        }
    }
}

