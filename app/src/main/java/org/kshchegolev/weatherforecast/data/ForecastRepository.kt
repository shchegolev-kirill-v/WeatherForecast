package org.kshchegolev.weatherforecast.data

import org.kshchegolev.weatherforecast.domain.Result
import org.kshchegolev.weatherforecast.domain.models.Forecast

interface ForecastRepository {
    suspend fun getForecast(location: String, days: Int): Result<Forecast>
}