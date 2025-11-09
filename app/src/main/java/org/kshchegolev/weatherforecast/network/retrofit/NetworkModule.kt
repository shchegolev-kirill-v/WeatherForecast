package org.kshchegolev.weatherforecast.network.retrofit

import org.kshchegolev.weatherforecast.network.ForecastApi
import org.kshchegolev.weatherforecast.network.ForecastApiImpl

class NetworkModule {

    fun provideForeCastApi(): ForecastApi {
        return ForecastApiImpl(
            RetrofitInstance().createApi(RetrofitForecastApi::class.java)
        )
    }
}