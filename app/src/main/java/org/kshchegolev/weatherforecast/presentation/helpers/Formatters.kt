package org.kshchegolev.weatherforecast.presentation.helpers

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Formatters {

    private val zoneId = ZoneId.systemDefault()
    private val hourMinutesFormatter = DateTimeFormatter.ofPattern("HH:mm").withZone(zoneId)
    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM").withZone(zoneId)

    fun Long?.toLocalTimeFormattedOrEmpty(): String =
        this?.let {
            val instant = Instant.ofEpochSecond(this)
            hourMinutesFormatter.format(instant)
        } ?: ""


    fun Long?.toLocalDateFormattedOrEmpty(): String =
        this?.let {
            val instant = Instant.ofEpochSecond(this)
            dateFormatter.format(instant)
        } ?: ""

    fun Double?.toTemperatureOrDefault(default: String = "--°"): String =
        when {
            this == null -> default
            this.isNaN() || this.isInfinite() -> default
            else -> "%.0f°".format(this)
        }

    fun String?.toCompleteUrlOrNull(): String? = this?.trim()?.let {"https:$this"}

}