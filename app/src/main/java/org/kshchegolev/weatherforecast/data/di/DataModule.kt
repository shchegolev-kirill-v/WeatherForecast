package org.kshchegolev.weatherforecast.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.kshchegolev.weatherforecast.data.ForecastRepository
import org.kshchegolev.weatherforecast.data.ForecastRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindForecastRepository(
        forecastRepository: ForecastRepositoryImpl
    ): ForecastRepository
}
