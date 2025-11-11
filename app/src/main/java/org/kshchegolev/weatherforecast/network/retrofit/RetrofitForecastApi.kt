package org.kshchegolev.weatherforecast.network.retrofit

import org.kshchegolev.weatherforecast.BuildConfig
import org.kshchegolev.weatherforecast.network.models.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitForecastApi {

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") location: String = LOCATION,
        @Query("days") days: Int = DAYS_COUNT,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): ForecastResponse

    private companion object {
        const val DAYS_COUNT = 3
        const val LOCATION = "55.7569,37.6151"
    }
}