package org.kshchegolev.weatherforecast.presentation.views.dsl

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

inline fun <T> View.bind(
    liveData: LiveData<T>,
    crossinline action: View.(T) -> Unit
) {
    liveData.observe(this.context as LifecycleOwner) { value ->
        action(value)
    }
}
