package org.kshchegolev.weatherforecast.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.kshchegolev.weatherforecast.network.ForecastApi
import org.kshchegolev.weatherforecast.network.ForecastApiImpl
import org.kshchegolev.weatherforecast.network.retrofit.RetrofitForecastApi
import org.kshchegolev.weatherforecast.network.retrofit.RetrofitInstance
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideForeCastApi(): ForecastApi {
        return ForecastApiImpl(
            RetrofitInstance().createApi(RetrofitForecastApi::class.java)
        )
    }
}