package org.kshchegolev.weatherforecast.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kshchegolev.weatherforecast.domain.Result
import org.kshchegolev.weatherforecast.domain.models.CurrentWeather
import org.kshchegolev.weatherforecast.domain.models.DailyForecast
import org.kshchegolev.weatherforecast.domain.models.HourlyForecast
import org.kshchegolev.weatherforecast.domain.usecases.GetForecastUseCase
import org.kshchegolev.weatherforecast.network.models.ForecastResponse
import org.kshchegolev.weatherforecast.presentation.helpers.Formatters
import org.kshchegolev.weatherforecast.presentation.helpers.Formatters.convertEpochToLocalTimeFormatted
import org.kshchegolev.weatherforecast.presentation.helpers.Formatters.toCompleteUrl
import org.kshchegolev.weatherforecast.presentation.helpers.Formatters.toTemperatureOrDefault
import org.kshchegolev.weatherforecast.presentation.models.Panel
import org.kshchegolev.weatherforecast.presentation.models.UiState
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
internal class ForecastViewModel @Inject constructor(
    private val forecastUseCase: GetForecastUseCase
) : ViewModel() {

    private var initialized = false

    private var loadDataJob: Job? = null

    private val stateMutable = MutableLiveData(UiState.EMPTY)
    val state: LiveData<UiState> = stateMutable

    val title: LiveData<String> = state.map { it.title }.distinctUntilChanged()
    val currentWeather: LiveData<CurrentWeather> =
        state.map { it.currentWeather }.distinctUntilChanged()
    val dailyForecast: LiveData<List<DailyForecast>> =
        state.map { it.dailyForecasts }.distinctUntilChanged()
    val hourlyForecast: LiveData<List<HourlyForecast>> =
        state.map { it.hourlyForecasts }.distinctUntilChanged()
    val showPanel: LiveData<Panel> =
        state.map { it.getPanelToShow() }.distinctUntilChanged()

    fun initialize() {
        if (!initialized) {
            initialized = true
            loadData()
        }
    }

    fun refreshData() {
        loadData(isRefreshing = true)
    }

    fun loadData(isRefreshing: Boolean = false) {
        loadDataJob?.let { job ->
            if (job.isActive) {
                job.cancel(CancellationException("New loadData request"))
            }
        }
        loadDataJob = viewModelScope.launch {
            stateMutable.value = stateMutable.value?.copy(
                isLoading = true,
                isRefreshing = isRefreshing,
                isError = false
            )

            val result = forecastUseCase.getForecast()
            when (result) {
                is Result.Failure -> {
                    handleErrorResult()
                }
                is Result.Success -> {
                    handleSuccessResult(result.data)
                }
            }
        }
    }

    fun snackBarShown() {
        stateMutable.value = stateMutable.value?.copy(
            shouldShowErrorSnackBar = false
        )
    }

    private fun handleSuccessResult(result: ForecastResponse) {
        stateMutable.value = stateMutable.value?.copy(
            isLoading = false,
            isRefreshing = false,
            isError = false,
            shouldShowErrorSnackBar = false,
            title = result.location.name,
            currentWeather = CurrentWeather(
                temp = result.current.temp.toTemperatureOrDefault(),
                iconUrl = result.current.condition.iconUrl.toCompleteUrl(),
                updatedAt = convertEpochToLocalTimeFormatted(Instant.now().epochSecond),
            ),
            hourlyForecasts = result.forecast.forecastDays[0].hour.map {
                HourlyForecast(
                    hour = convertEpochToLocalTimeFormatted(it.timestamp),
                    temp = it.temp.toTemperatureOrDefault(),
                    iconUrl = it.condition.iconUrl.toCompleteUrl(),
                    timestamp = it.timestamp,
                )
            },
            dailyForecasts = result.forecast.forecastDays.map {
                DailyForecast(
                    day = Formatters.convertEpochToLocalDateFormatted(
                        it.timestamp
                    ),
                    tempMax = it.day.maxTemp.toTemperatureOrDefault(),
                    tempMin = it.day.minTemp.toTemperatureOrDefault(),
                    iconUrl = it.day.condition.iconUrl.toCompleteUrl()
                )
            }
        )
    }

    private fun handleErrorResult() {
        val shouldShowErrorSnackBar = stateMutable.value?.run {
            dailyForecasts.isNotEmpty()
        } ?: false
        stateMutable.value = stateMutable.value?.copy(
            isLoading = false,
            isRefreshing = false,
            isError = true,
            shouldShowErrorSnackBar = shouldShowErrorSnackBar
        )
    }
}

private fun UiState.getPanelToShow(): Panel {
    return when {
        isLoading && !isRefreshing -> Panel.Loading
        isLoading && isRefreshing -> Panel.Content
        isError && dailyForecasts.isNotEmpty() -> Panel.Content
        isError -> Panel.Error
        else -> Panel.Content
    }
}


