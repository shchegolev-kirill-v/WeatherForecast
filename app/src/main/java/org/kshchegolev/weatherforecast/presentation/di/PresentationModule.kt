package org.kshchegolev.weatherforecast.presentation.di

import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import org.kshchegolev.weatherforecast.presentation.mappers.ForecastDomainToCurrentWeatherUiMapper
import org.kshchegolev.weatherforecast.presentation.mappers.ForecastDomainToCurrentWeatherUiMapperImpl
import org.kshchegolev.weatherforecast.presentation.mappers.ForecastDomainToDailyForecastUiMapper
import org.kshchegolev.weatherforecast.presentation.mappers.ForecastDomainToDailyForecastUiMapperImpl
import org.kshchegolev.weatherforecast.presentation.mappers.ForecastDomainToHourlyForecastUiMapper
import org.kshchegolev.weatherforecast.presentation.mappers.ForecastDomainToHourlyForecastUiMapperImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class PresentationModule {


    @Binds
    @Reusable
    abstract fun bindForecastDomainToCurrentWeatherUiMapper(
        forecastDomainToCurrentWeatherUiMapper: ForecastDomainToCurrentWeatherUiMapperImpl
    ): ForecastDomainToCurrentWeatherUiMapper

    @Binds
    @Reusable
    abstract fun bindForecastDomainToDailyForecastUiMapper(
        forecastDomainToDailyForecastUiMapper: ForecastDomainToDailyForecastUiMapperImpl
    ): ForecastDomainToDailyForecastUiMapper

    @Binds
    @Reusable
    abstract fun bindForecastDomainToHourlyForecastUiMapper(
        forecastDomainToHourlyForecastUiMapper: ForecastDomainToHourlyForecastUiMapperImpl
    ): ForecastDomainToHourlyForecastUiMapper
}
