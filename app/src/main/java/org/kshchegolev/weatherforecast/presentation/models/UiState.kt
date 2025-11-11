package org.kshchegolev.weatherforecast.presentation.models

data class UiState(
    val isLoading: Boolean,
    val isRefreshing: Boolean,
    val isError: Boolean,
    val shouldShowErrorSnackBar: Boolean,
    val title: String,
    val currentWeather: CurrentWeatherUi,
    val hourlyForecasts: List<HourlyForecastUi>,
    val dailyForecasts: List<DailyForecastUi>
) {

    companion object {
        val EMPTY =
            UiState(
                isLoading = false,
                isRefreshing = false,
                isError = false,
                shouldShowErrorSnackBar = false,
                title = "",
                currentWeather = CurrentWeatherUi(
                    temp = "",
                    iconUrl = "",
                    updatedAt = ""
                ),
                hourlyForecasts = emptyList(),
                dailyForecasts = emptyList()
            )
    }
}