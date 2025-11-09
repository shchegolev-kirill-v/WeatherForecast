package org.kshchegolev.weatherforecast.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.kshchegolev.weatherforecast.data.ForecastRepository
import org.kshchegolev.weatherforecast.data.ForecastRepositoryImpl
import org.kshchegolev.weatherforecast.di.DefaultDispatcher
import org.kshchegolev.weatherforecast.domain.Result
import org.kshchegolev.weatherforecast.network.models.ForecastResponse
import javax.inject.Inject

class GetForecastUseCaseImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val forecastRepository: ForecastRepository
) : GetForecastUseCase {
    override suspend fun getForecast(): Result<ForecastResponse> =
        withContext(defaultDispatcher) {
            forecastRepository.getForecast()
        }
}