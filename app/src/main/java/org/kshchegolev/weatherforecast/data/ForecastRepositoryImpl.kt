package org.kshchegolev.weatherforecast.data

import org.kshchegolev.weatherforecast.domain.Result
import org.kshchegolev.weatherforecast.network.ForecastApi
import org.kshchegolev.weatherforecast.network.models.ForecastResponse
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val forecastApi: ForecastApi
) : ForecastRepository {

    override suspend fun getForecast(): Result<ForecastResponse> = forecastApi.getForecast()
}