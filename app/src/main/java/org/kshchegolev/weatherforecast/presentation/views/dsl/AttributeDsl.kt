package org.kshchegolev.weatherforecast.presentation.views.dsl

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout

fun View.size(width: Int, height: Int) {
    val params = layoutParams ?: ViewGroup.LayoutParams(width, height)
    params.width = width
    params.height = height
    layoutParams = params
}

fun View.width(width: Int) {
    val current = layoutParams ?: ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    current.width = width
    layoutParams = current
}

fun View.height(height: Int) {
    val current = layoutParams ?: ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    current.height = height
    layoutParams = current
}

fun View.weight(weight: Float) {
    val params = layoutParams as? LinearLayout.LayoutParams
    if (params != null) {
        params.weight = weight
        layoutParams = params
    }
}

fun View.matchParentWidth() {
    width(MATCH_PARENT)
}

fun View.matchParentHeight() {
    height(MATCH_PARENT)
}

fun View.wrapContentWidth() {
    width(WRAP_CONTENT)
}

fun View.wrapContentHeight() {
    height(WRAP_CONTENT)
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density)