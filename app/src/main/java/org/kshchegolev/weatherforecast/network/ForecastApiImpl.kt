package org.kshchegolev.weatherforecast.network

import kotlinx.coroutines.CancellationException
import org.kshchegolev.weatherforecast.domain.Result
import org.kshchegolev.weatherforecast.network.models.ForecastResponse
import org.kshchegolev.weatherforecast.network.retrofit.RetrofitForecastApi
import org.kshchegolev.weatherforecast.network.retrofit.RetrofitInstance

class ForecastApiImpl(
    private val retrofitForecastApi: RetrofitForecastApi = RetrofitInstance().createApi(
        RetrofitForecastApi::class.java
    )
) : ForecastApi {
    override suspend fun getForecast(): Result<ForecastResponse> {
        return try {
            Result.Success(retrofitForecastApi.getForecast())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.Failure("Failed to get forecast: ${e.message}")
        }

    }
}

interface ForecastApi {
    suspend fun getForecast(): Result<ForecastResponse>
}