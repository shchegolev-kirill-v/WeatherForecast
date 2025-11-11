package org.kshchegolev.weatherforecast.data

import org.kshchegolev.weatherforecast.domain.Result
import org.kshchegolev.weatherforecast.domain.models.Forecast
import org.kshchegolev.weatherforecast.network.models.ForecastResponse

interface ForecastRepository {
    suspend fun getForecast(): Result<Forecast>
}