package org.kshchegolev.weatherforecast.presentation.views.components

import android.view.ViewGroup
import androidx.lifecycle.LiveData
import org.kshchegolev.weatherforecast.R
import org.kshchegolev.weatherforecast.presentation.views.dsl.alwaysShow
import org.kshchegolev.weatherforecast.presentation.views.dsl.appBarLayout
import org.kshchegolev.weatherforecast.presentation.views.dsl.bind
import org.kshchegolev.weatherforecast.presentation.views.dsl.matchParentWidth
import org.kshchegolev.weatherforecast.presentation.views.dsl.toolbar
import org.kshchegolev.weatherforecast.presentation.views.dsl.wrapContentHeight
import kotlin.text.ifEmpty

internal fun ViewGroup.weatherAppBar(
    titleText: LiveData<String>
) =
    appBarLayout {
        matchParentWidth()
        wrapContentHeight()
        toolbar {
            matchParentWidth()
            wrapContentHeight()
            alwaysShow()
            bind(titleText) { it ->
                title = it.ifEmpty { context.getString(R.string.app_name) }
            }
        }
    }