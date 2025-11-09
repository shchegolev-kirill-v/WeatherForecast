package org.kshchegolev.weatherforecast.network.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> createApi(apiClass: Class<T>): T =
        retrofit.create(apiClass)

    private companion object {
        const val BASE_URL = "https://api.weatherapi.com/v1/"
    }
}