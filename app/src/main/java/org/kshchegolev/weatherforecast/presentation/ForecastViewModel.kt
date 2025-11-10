package org.kshchegolev.weatherforecast.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.kshchegolev.weatherforecast.domain.Result
import org.kshchegolev.weatherforecast.domain.models.CurrentWeather
import org.kshchegolev.weatherforecast.domain.models.DailyForecast
import org.kshchegolev.weatherforecast.domain.models.HourlyForecast
import org.kshchegolev.weatherforecast.domain.usecases.GetForecastUseCase
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val forecastUseCase: GetForecastUseCase
) : ViewModel() {

    private var initialized = false
    private val uiStateMutable = MutableStateFlow(UiState.EMPTY)
    val uiState = uiStateMutable.asStateFlow()

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
        viewModelScope.launch {
            //TODO cancel previous job

            stateMutable.value = stateMutable.value?.copy(
                isLoading = true,
                isRefreshing = isRefreshing,
                isError = false
            )

            val result = forecastUseCase.getForecast()
            when (result) {
                is Result.Failure -> {
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

                is Result.Success -> {
                    stateMutable.value = stateMutable.value?.copy(
                        isLoading = false,
                        isRefreshing = false,
                        isError = false,
                        shouldShowErrorSnackBar = false,
                        title = result.data.location.name,
                        currentWeather = CurrentWeather(
                            temp = result.data.current.temp.toString(), //TODO
                            iconUrl = "https:${result.data.current.condition.iconUrl}",
                            updatedAt = convertEpochToLocalTimeFormatted(Instant.now().epochSecond),
                        ),
                        hourlyForecasts = result.data.forecast.forecastDays[0].hour.map {
                            HourlyForecast(
                                hour = convertEpochToLocalTimeFormatted(it.timestamp),
                                temp = it.temp.toString(),
                                iconUrl = "https://cdn.weatherapi.com/weather/64x64/day/122.png", //TODO
                                timestamp = it.timestamp,
                            )
                        },
                        dailyForecasts = result.data.forecast.forecastDays.map {
                            DailyForecast(
                                day = it.date,
                                tempMax = it.day.maxTemp.toString(),
                                tempMin = it.day.minTemp.toString(),
                                iconUrl = "https:${it.day.condition.iconUrl}",
                                timestamp = 100
                            )
                        }
                    )
                }
            }
        }
    }

    fun snackBarShown() {
        stateMutable.value = stateMutable.value?.copy(
            shouldShowErrorSnackBar = false
        )
    }

    override fun onCleared() {
        super.onCleared()
    }
}

private fun convertEpochToLocalTimeFormatted(epochSeconds: Long): String {
    val instant = Instant.ofEpochSecond(epochSeconds)
    val zoneId = ZoneId.systemDefault()
    val formatter = DateTimeFormatter.ofPattern("HH:mm").withZone(zoneId)

    return formatter.format(instant)
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


data class UiState(
    val isLoading: Boolean,
    val isRefreshing: Boolean,
    val isError: Boolean,
    val shouldShowErrorSnackBar: Boolean,
    val title: String,
    val currentWeather: CurrentWeather,
    val hourlyForecasts: List<HourlyForecast>,
    val dailyForecasts: List<DailyForecast>
) {

    companion object {
        val EMPTY =
            UiState(
                isLoading = false,
                isRefreshing = false,
                isError = false,
                shouldShowErrorSnackBar = false,
                title = "",
                currentWeather = CurrentWeather(
                    temp = "",
                    iconUrl = "",
                    updatedAt = ""
                ),
                hourlyForecasts = emptyList(),
                dailyForecasts = emptyList()
            )
    }
}

enum class Panel {
    Loading, Error, Content
}