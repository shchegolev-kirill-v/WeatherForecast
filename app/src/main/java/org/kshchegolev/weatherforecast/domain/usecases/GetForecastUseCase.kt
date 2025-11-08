package org.kshchegolev.weatherforecast.domain.usecases

import kotlinx.coroutines.flow.Flow

interface GetForecastUseCase {
    fun getForecastFlow(): Flow<String>
}

