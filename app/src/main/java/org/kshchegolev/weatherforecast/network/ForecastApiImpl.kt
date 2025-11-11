package org.kshchegolev.weatherforecast.network

import kotlinx.coroutines.CancellationException
import org.kshchegolev.weatherforecast.domain.Result
import org.kshchegolev.weatherforecast.network.models.ForecastResponse
import org.kshchegolev.weatherforecast.network.retrofit.RetrofitForecastApi
import javax.inject.Inject

class ForecastApiImpl @Inject constructor(
    private val retrofitForecastApi: RetrofitForecastApi
) : ForecastApi {
    override suspend fun getForecast(location: String, days: Int): Result<ForecastResponse> {
        return try {
            Result.Success(retrofitForecastApi.getForecast(location = location, days = days))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.Failure("Failed to get forecast: ${e.message}")
        }
    }
}
