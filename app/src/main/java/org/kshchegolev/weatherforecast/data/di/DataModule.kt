package org.kshchegolev.weatherforecast.data.di

import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.kshchegolev.weatherforecast.data.ForecastRepository
import org.kshchegolev.weatherforecast.data.ForecastRepositoryImpl
import org.kshchegolev.weatherforecast.data.mappers.ForecastResponseDtoToDomainMapper
import org.kshchegolev.weatherforecast.data.mappers.ForecastResponseDtoToDomainMapperImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindForecastRepository(
        forecastRepository: ForecastRepositoryImpl
    ): ForecastRepository

    @Binds
    @Reusable
    abstract fun bindForecastResponseDtoToDomainMapper(
        forecastResponseDtoToDomainMapper: ForecastResponseDtoToDomainMapperImpl
    ): ForecastResponseDtoToDomainMapper
}
