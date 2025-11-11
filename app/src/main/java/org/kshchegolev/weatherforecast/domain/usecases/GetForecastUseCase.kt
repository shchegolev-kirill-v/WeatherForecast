package org.kshchegolev.weatherforecast.domain.usecases

import org.kshchegolev.weatherforecast.domain.Result
import org.kshchegolev.weatherforecast.domain.models.Forecast

interface GetForecastUseCase {
    suspend fun getForecast(location: String = LOCATION, days: Int = DAYS_COUNT): Result<Forecast>

    private companion object {
        const val DAYS_COUNT = 3
        const val LOCATION = "55.7569,37.6151"
    }
}

