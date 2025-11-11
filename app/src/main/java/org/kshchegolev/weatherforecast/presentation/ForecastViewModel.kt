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
import org.kshchegolev.weatherforecast.domain.models.Forecast
import org.kshchegolev.weatherforecast.domain.usecases.GetForecastUseCase
import org.kshchegolev.weatherforecast.presentation.enums.Panel
import org.kshchegolev.weatherforecast.presentation.mappers.ForecastDomainToCurrentWeatherUiMapper
import org.kshchegolev.weatherforecast.presentation.mappers.ForecastDomainToDailyForecastUiMapper
import org.kshchegolev.weatherforecast.presentation.mappers.ForecastDomainToHourlyForecastUiMapper
import org.kshchegolev.weatherforecast.presentation.models.CurrentWeatherUi
import org.kshchegolev.weatherforecast.presentation.models.DailyForecastUi
import org.kshchegolev.weatherforecast.presentation.models.HourlyForecastUi
import org.kshchegolev.weatherforecast.presentation.models.UiState
import javax.inject.Inject

@HiltViewModel
internal class ForecastViewModel @Inject constructor(
    private val forecastUseCase: GetForecastUseCase,
    private val forecastDomainToCurrentWeatherUiMapper: ForecastDomainToCurrentWeatherUiMapper,
    private val forecastDomainToHourlyForecastUiMapper: ForecastDomainToHourlyForecastUiMapper,
    private val forecastDomainToDailyForecastUiMapper: ForecastDomainToDailyForecastUiMapper
) : ViewModel() {

    private var initialized = false

    private var loadDataJob: Job? = null

    private val stateMutable = MutableLiveData(UiState.EMPTY)
    val state: LiveData<UiState> = stateMutable

    val title: LiveData<String> = state.map { it.title }.distinctUntilChanged()
    val currentWeather: LiveData<CurrentWeatherUi> =
        state.map { it.currentWeather }.distinctUntilChanged()
    val dailyForecast: LiveData<List<DailyForecastUi>> =
        state.map { it.dailyForecasts }.distinctUntilChanged()
    val hourlyForecast: LiveData<List<HourlyForecastUi>> =
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
        loadDataJob.cancelIfActive()
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

    private fun handleSuccessResult(forecast: Forecast) {
        stateMutable.value = stateMutable.value?.copy(
            isLoading = false,
            isRefreshing = false,
            isError = false,
            shouldShowErrorSnackBar = false,
            title = forecast.locationName ?: "",
            currentWeather = forecastDomainToCurrentWeatherUiMapper.map(forecast),
            hourlyForecasts = forecastDomainToHourlyForecastUiMapper.map(forecast),
            dailyForecasts = forecastDomainToDailyForecastUiMapper.map(forecast)
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

private fun Job?.cancelIfActive() =
    this?.let { job ->
        if (isActive) {
            cancel(CancellationException("New loadData request"))
        }
    }

private fun UiState.getPanelToShow(): Panel =
    when {
        isLoading && !isRefreshing -> Panel.Loading
        isLoading && isRefreshing -> Panel.Content
        isError && dailyForecasts.isNotEmpty() -> Panel.Content
        isError -> Panel.Error
        else -> Panel.Content
    }


