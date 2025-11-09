package org.kshchegolev.weatherforecast.network

import org.kshchegolev.weatherforecast.domain.Result
import org.kshchegolev.weatherforecast.network.models.ForecastResponse

interface ForecastApi {
    suspend fun getForecast(): Result<ForecastResponse>
}