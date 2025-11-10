package org.kshchegolev.weatherforecast.presentation.views.components.screenpanels

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import com.google.android.material.R.style
import org.kshchegolev.weatherforecast.R
import org.kshchegolev.weatherforecast.presentation.models.Panel
import org.kshchegolev.weatherforecast.presentation.views.dsl.bind
import org.kshchegolev.weatherforecast.presentation.views.dsl.button
import org.kshchegolev.weatherforecast.presentation.views.dsl.dp
import org.kshchegolev.weatherforecast.presentation.views.dsl.matchParentHeight
import org.kshchegolev.weatherforecast.presentation.views.dsl.matchParentWidth
import org.kshchegolev.weatherforecast.presentation.views.dsl.size
import org.kshchegolev.weatherforecast.presentation.views.dsl.space
import org.kshchegolev.weatherforecast.presentation.views.dsl.textView
import org.kshchegolev.weatherforecast.presentation.views.dsl.verticalLayout
import org.kshchegolev.weatherforecast.presentation.views.dsl.wrapContentHeight
import org.kshchegolev.weatherforecast.presentation.views.dsl.wrapContentWidth

internal fun ViewGroup.errorPanel(
    showPanel: LiveData<Panel>,
    onRetry: () -> Unit
) =
    verticalLayout {
        matchParentWidth()
        matchParentHeight()
        gravity = Gravity.CENTER
        bind(showPanel) {
            visibility =
                if (it == Panel.Error) View.VISIBLE else View.GONE
        }
        textView {
            wrapContentWidth()
            wrapContentHeight()
            setTextAppearance(style.TextAppearance_Material3_BodyLarge)
            setTextColor(context.getColor(R.color.md_theme_secondary))
            text = context.getString(R.string.error_msg)
        }
        space { size(0.dp, 24.dp) }
        button {
            wrapContentWidth()
            wrapContentHeight()
            setTextAppearance(style.TextAppearance_MaterialComponents_Button)
            setTextColor(context.getColor(R.color.md_theme_onPrimary))
            text = context.getString(R.string.reload_btn)
            setOnClickListener { onRetry() }
        }
    }