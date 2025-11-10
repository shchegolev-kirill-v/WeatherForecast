package org.kshchegolev.weatherforecast.presentation.views.dsl

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.textview.MaterialTextView

inline fun <T> View.bindState(
    liveData: LiveData<T>,
    crossinline updater: View.(T) -> Unit
) {
    liveData.observe(this.context as LifecycleOwner) { state ->
        updater(state)
    }
}

fun MaterialTextView.bindTextState(liveData: LiveData<TextState>) {
    bindState(liveData) { state ->
        text = state.text
        visibility = state.visibility
    }
}

data class TextState(
    val text: String,
    val visibility: Int = View.VISIBLE
)