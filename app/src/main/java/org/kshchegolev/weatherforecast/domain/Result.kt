package org.kshchegolev.weatherforecast.domain

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure<T>(val message: String) : Result<T>()
}
