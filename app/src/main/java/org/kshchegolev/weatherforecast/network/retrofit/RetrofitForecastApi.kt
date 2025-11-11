package org.kshchegolev.weatherforecast.network.retrofit

import org.kshchegolev.weatherforecast.BuildConfig
import org.kshchegolev.weatherforecast.network.models.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitForecastApi {

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): ForecastResponse
}