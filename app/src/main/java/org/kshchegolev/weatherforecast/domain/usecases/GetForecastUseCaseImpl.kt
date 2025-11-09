package org.kshchegolev.weatherforecast.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.kshchegolev.weatherforecast.data.ForecastRepository
import org.kshchegolev.weatherforecast.data.ForecastRepositoryImpl
import org.kshchegolev.weatherforecast.domain.Result
import org.kshchegolev.weatherforecast.network.models.ForecastResponse

class GetForecastUseCaseImpl(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val forecastRepository: ForecastRepository = ForecastRepositoryImpl()
) : GetForecastUseCase {
    override suspend fun getForecast(): Result<ForecastResponse> =
        withContext(defaultDispatcher) {
            forecastRepository.getForecast()
        }
}