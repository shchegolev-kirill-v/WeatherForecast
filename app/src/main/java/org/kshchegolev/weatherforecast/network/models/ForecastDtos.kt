package org.kshchegolev.weatherforecast.network.models

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("location") val location: LocationDto?,
    @SerializedName("current") val current: CurrentDto?,
    @SerializedName("forecast") val forecast: ForecastDto?
)

data class LocationDto(
    @SerializedName("name") val name: String?
)

data class CurrentDto(
    @SerializedName("temp_c") val temp: Double?,
    @SerializedName("condition") val condition: ConditionDto?
)

data class ConditionDto(
    @SerializedName("icon") val iconUrl: String?
)

data class ForecastDto(
    @SerializedName("forecastday") val forecastDays: List<ForecastDayDto>?
)

data class ForecastDayDto(
    @SerializedName("date_epoch") val timestamp: Long?,
    @SerializedName("day") val day: DayDto?,
    @SerializedName("hour") val hours: List<HourDto>?
)

data class DayDto(
    @SerializedName("maxtemp_c") val maxTemp: Double?,
    @SerializedName("mintemp_c") val minTemp: Double?,
    @SerializedName("condition") val condition: ConditionDto?
)

data class HourDto(
    @SerializedName("time_epoch") val timestamp: Long?,
    @SerializedName("temp_c") val temp: Double?,
    @SerializedName("condition") val condition: ConditionDto?
)
