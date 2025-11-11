package org.kshchegolev.weatherforecast.domain.usecases

import org.kshchegolev.weatherforecast.domain.Result
import org.kshchegolev.weatherforecast.domain.models.Forecast

interface GetForecastUseCase {
    suspend fun getForecast(): Result<Forecast>
}

