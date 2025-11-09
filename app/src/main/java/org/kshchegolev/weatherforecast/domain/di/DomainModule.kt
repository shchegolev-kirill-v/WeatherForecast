package org.kshchegolev.weatherforecast.domain.di

import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.kshchegolev.weatherforecast.data.ForecastRepository
import org.kshchegolev.weatherforecast.data.ForecastRepositoryImpl
import org.kshchegolev.weatherforecast.domain.usecases.GetForecastUseCase
import org.kshchegolev.weatherforecast.domain.usecases.GetForecastUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    @Reusable
    abstract fun bindGetForecastUseCase(
        getForecastUseCase: GetForecastUseCaseImpl
    ): GetForecastUseCase
}
