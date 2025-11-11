package org.kshchegolev.weatherforecast.presentation.views.components.cards

import android.view.Gravity
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import coil3.load
import com.google.android.material.R.style
import org.kshchegolev.weatherforecast.R
import org.kshchegolev.weatherforecast.presentation.models.DailyForecastUi
import org.kshchegolev.weatherforecast.presentation.views.dsl.bind
import org.kshchegolev.weatherforecast.presentation.views.dsl.cardView
import org.kshchegolev.weatherforecast.presentation.views.dsl.dp
import org.kshchegolev.weatherforecast.presentation.views.dsl.horizontalLayout
import org.kshchegolev.weatherforecast.presentation.views.dsl.imageView
import org.kshchegolev.weatherforecast.presentation.views.dsl.matchParentWidth
import org.kshchegolev.weatherforecast.presentation.views.dsl.size
import org.kshchegolev.weatherforecast.presentation.views.dsl.textView
import org.kshchegolev.weatherforecast.presentation.views.dsl.verticalLayout
import org.kshchegolev.weatherforecast.presentation.views.dsl.weight
import org.kshchegolev.weatherforecast.presentation.views.dsl.wrapContentHeight
import org.kshchegolev.weatherforecast.presentation.views.dsl.wrapContentWidth

internal fun ViewGroup.dailyForecastCard(
    dailyForecast: LiveData<List<DailyForecastUi>>
) =
    cardView {
        matchParentWidth()
        wrapContentHeight()
        cardElevation = 8f.dp
        setContentPadding(24.dp, 24.dp, 24.dp, 24.dp)
        verticalLayout {
            matchParentWidth()
            wrapContentHeight()
            bind(dailyForecast) { items ->
                removeAllViews()
                items.map { forecast ->
                    dailyForecastItem(forecast)
                }
            }
        }
    }

private fun ViewGroup.dailyForecastItem(
    dailyForecast: DailyForecastUi
) =
    horizontalLayout {
        matchParentWidth()
        wrapContentHeight()
        gravity = Gravity.CENTER_VERTICAL
        textView {
            weight(0.1f)
            wrapContentHeight()
            setTextAppearance(style.TextAppearance_Material3_BodyLarge_Emphasized)
            setTextColor(context.getColor(R.color.md_theme_primary))
            text = dailyForecast.day
        }
        imageView {
            size(48.dp, 48.dp)
            load(dailyForecast.iconUrl)
        }
        textView {
            wrapContentWidth()
            wrapContentHeight()
            setTextAppearance(style.TextAppearance_Material3_HeadlineMedium)
            setTextColor(context.getColor(R.color.md_theme_primary))
            setPadding(12.dp, 0.dp, 0.dp, 0.dp)
            text = dailyForecast.tempMax
        }
        textView {
            wrapContentHeight()
            setTextAppearance(style.TextAppearance_Material3_HeadlineSmall)
            setTextColor(context.getColor(R.color.md_theme_secondary))
            setPadding(12.dp, 0.dp, 0.dp, 0.dp)
            text = dailyForecast.tempMin
        }
    }