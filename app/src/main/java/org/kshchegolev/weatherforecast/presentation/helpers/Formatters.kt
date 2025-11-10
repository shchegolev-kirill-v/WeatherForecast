package org.kshchegolev.weatherforecast.presentation.helpers

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Formatters {

    private val zoneId = ZoneId.systemDefault()
    private val hourMinutesFormatter = DateTimeFormatter.ofPattern("HH:mm").withZone(zoneId)
    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM").withZone(zoneId)

    fun convertEpochToLocalTimeFormatted(epochSeconds: Long): String {
        val instant = Instant.ofEpochSecond(epochSeconds)
        return hourMinutesFormatter.format(instant)
    }

    fun convertEpochToLocalDateFormatted(epochSeconds: Long): String {
        val instant = Instant.ofEpochSecond(epochSeconds)
        return dateFormatter.format(instant)
    }

    fun Double?.toTemperatureOrDefault(default: String = "--°"): String =
        when {
            this == null -> default
            this.isNaN() || this.isInfinite() -> default
            else -> "%.0f°".format(this)
        }

    fun String.toCompleteUrl(): String = "https:$this"

}