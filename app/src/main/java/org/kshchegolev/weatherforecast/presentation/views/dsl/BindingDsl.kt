package org.kshchegolev.weatherforecast.presentation.views.dsl

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged

inline fun <T> View.bind(
    liveData: LiveData<T>,
    crossinline action: View.(T) -> Unit
) {
    val distinctLiveData = liveData.distinctUntilChanged()
    distinctLiveData.observe(this.context as LifecycleOwner) { value ->
        action(value)
    }
}
