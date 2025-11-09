package org.kshchegolev.weatherforecast.data

import org.kshchegolev.weatherforecast.domain.Result
import org.kshchegolev.weatherforecast.network.ForecastApi
import org.kshchegolev.weatherforecast.network.ForecastApiImpl
import org.kshchegolev.weatherforecast.network.models.ForecastResponse

class ForecastRepositoryImpl(
    private val forecastApi: ForecastApi = ForecastApiImpl()
) : ForecastRepository {

    override suspend fun getForecast(): Result<ForecastResponse> = forecastApi.getForecast()
}