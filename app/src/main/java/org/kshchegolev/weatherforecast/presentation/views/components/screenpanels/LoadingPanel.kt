package org.kshchegolev.weatherforecast.presentation.views.components.screenpanels

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import org.kshchegolev.weatherforecast.presentation.enums.Panel
import org.kshchegolev.weatherforecast.presentation.views.dsl.bind
import org.kshchegolev.weatherforecast.presentation.views.dsl.dp
import org.kshchegolev.weatherforecast.presentation.views.dsl.height
import org.kshchegolev.weatherforecast.presentation.views.dsl.matchParentWidth
import org.kshchegolev.weatherforecast.presentation.views.dsl.progress
import org.kshchegolev.weatherforecast.presentation.views.dsl.verticalLayout

internal fun ViewGroup.loadingPanel(
    showPanel: LiveData<Panel>
) =
    verticalLayout {
        matchParentWidth()
        height(200.dp)
        gravity = Gravity.CENTER
        bind(showPanel) {
            visibility =
                if (it == Panel.Loading) View.VISIBLE else View.GONE
        }
        progress {
            isIndeterminate = true
        }
    }