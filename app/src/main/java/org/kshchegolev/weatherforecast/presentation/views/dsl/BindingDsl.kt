package org.kshchegolev.weatherforecast.presentation.views.dsl

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map

inline fun <T> View.bind(
    liveData: LiveData<T>,
    crossinline action: View.(T) -> Unit
) {
    val distinctLiveData = liveData.distinctUntilChanged()
    distinctLiveData.observe(this.context as LifecycleOwner) { value ->
        action(value)
    }
}
/*
inline fun <T, R> View.bindDistinct(
    liveData: LiveData<T>,
    crossinline mapper: (T) -> R,
    crossinline updater: View.(R) -> Unit
) {
    val distinctLiveData = liveData
        .map { mapper(it) }
        .distinctUntilChanged()

    distinctLiveData.observe(this.context as LifecycleOwner) { value ->
        updater(value)
    }
}

inline fun <T> TextView.bindTextFrom(
    liveData: LiveData<T>,
    crossinline provider: (T) -> String
) = bindDistinct(liveData, provider) { it -> text = it }

inline fun <T> View.bindVisibleFrom(
    liveData: LiveData<T>,
    crossinline provider: (T) -> Boolean
) = bindDistinct(liveData, provider) { visible ->
    visibility = if (visible) View.VISIBLE else View.GONE
}*/
