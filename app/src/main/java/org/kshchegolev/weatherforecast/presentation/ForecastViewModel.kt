package org.kshchegolev.weatherforecast.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kshchegolev.weatherforecast.domain.Result
import org.kshchegolev.weatherforecast.domain.models.HourlyForecast
import org.kshchegolev.weatherforecast.domain.usecases.GetForecastUseCase
import org.kshchegolev.weatherforecast.domain.usecases.GetForecastUseCaseImpl
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ForecastViewModel(
    private val forecastUseCase: GetForecastUseCase = GetForecastUseCaseImpl()
) : ViewModel() {

    private val initialized = false
    private val uiStateMutable = MutableStateFlow(UiState.EMPTY)
    val uiState = uiStateMutable.asStateFlow()


    fun initialize() {
        if (!initialized) {
            loadData()
        }
    }

    private fun loadData(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            //TODO disable previous job

            uiStateMutable.update { it ->
                it.copy(
                    isLoading = true,
                    isRefreshing = isRefreshing
                )
            }

            val result = forecastUseCase.getForecast()
            when (result) {
                is Result.Failure -> {
                    println(result.message)
                }

                is Result.Success -> {
                    uiStateMutable.update { it ->
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            items = result.data.forecast.forecastDays[0].hour.map {
                                HourlyForecast(
                                    hour = convertEpochToLocalTime(it.timestamp),
                                    temp = it.temp.toString(),
                                    timestamp = it.timestamp,
                                )
                            }
                        )
                    }
                }
            }


        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}


fun convertEpochToLocalTime(epochSeconds: Long): String {
    val instant = Instant.ofEpochSecond(epochSeconds)
    val zoneId = ZoneId.systemDefault()
    val formatter = DateTimeFormatter.ofPattern("HH:mm").withZone(zoneId)

    return formatter.format(instant)
}

data class UiState(
    val isLoading: Boolean,
    val isRefreshing: Boolean,
    val items: List<HourlyForecast>
) {

    companion object {
        val EMPTY =
            UiState(
                isLoading = false,
                isRefreshing = false,
                items = emptyList()
            )
    }
}