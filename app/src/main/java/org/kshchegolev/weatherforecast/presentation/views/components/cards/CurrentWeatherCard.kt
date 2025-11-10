package org.kshchegolev.weatherforecast.presentation.views.components.cards

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import coil3.load
import com.google.android.material.R.style
import org.kshchegolev.weatherforecast.R
import org.kshchegolev.weatherforecast.domain.models.CurrentWeather
import org.kshchegolev.weatherforecast.presentation.views.dsl.bind
import org.kshchegolev.weatherforecast.presentation.views.dsl.cardView
import org.kshchegolev.weatherforecast.presentation.views.dsl.dp
import org.kshchegolev.weatherforecast.presentation.views.dsl.horizontalLayout
import org.kshchegolev.weatherforecast.presentation.views.dsl.imageView
import org.kshchegolev.weatherforecast.presentation.views.dsl.matchParentHeight
import org.kshchegolev.weatherforecast.presentation.views.dsl.matchParentWidth
import org.kshchegolev.weatherforecast.presentation.views.dsl.size
import org.kshchegolev.weatherforecast.presentation.views.dsl.textView
import org.kshchegolev.weatherforecast.presentation.views.dsl.wrapContentHeight
import org.kshchegolev.weatherforecast.presentation.views.dsl.wrapContentWidth

internal fun ViewGroup.currentWeatherCard(
    currentWeather: LiveData<CurrentWeather>,
) =
    cardView {
        cardElevation = 8f.dp
        setContentPadding(24.dp, 24.dp, 24.dp, 24.dp)
        horizontalLayout {
            textView {
                wrapContentWidth()
                wrapContentHeight()
                setTextAppearance(style.TextAppearance_Material3_DisplayLarge)
                setTextColor(context.getColor(R.color.md_theme_primary))
                bind(currentWeather) { weather ->
                    text = weather.temp
                }
            }
            imageView {
                size(64.dp, 64.dp)
                bind(currentWeather) { weather ->
                    if (weather.iconUrl.isNotEmpty()) {
                        load(weather.iconUrl)
                    }
                }
            }
            textView {
                matchParentWidth()
                matchParentHeight()
                textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                setTextAppearance(style.TextAppearance_MaterialComponents_Caption)
                setTextColor(context.getColor(R.color.md_theme_secondary))
                bind(currentWeather) { weather ->
                    text = context.getString(R.string.updated_at, weather.updatedAt)
                }
            }
        }
    }