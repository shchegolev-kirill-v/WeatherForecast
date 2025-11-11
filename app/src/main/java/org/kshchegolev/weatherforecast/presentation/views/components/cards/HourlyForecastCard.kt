package org.kshchegolev.weatherforecast.presentation.views.components.cards

import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import org.kshchegolev.weatherforecast.presentation.models.HourlyForecastUi
import org.kshchegolev.weatherforecast.presentation.HourlyForecastAdapter
import org.kshchegolev.weatherforecast.presentation.views.dsl.bind
import org.kshchegolev.weatherforecast.presentation.views.dsl.cardView
import org.kshchegolev.weatherforecast.presentation.views.dsl.dp
import org.kshchegolev.weatherforecast.presentation.views.dsl.matchParentWidth
import org.kshchegolev.weatherforecast.presentation.views.dsl.recyclerView
import org.kshchegolev.weatherforecast.presentation.views.dsl.wrapContentHeight

internal fun ViewGroup.hourlyForecastCard(
    hourlyForecast: LiveData<List<HourlyForecastUi>>
) =
    cardView {
        setContentPadding(12.dp, 12.dp, 12.dp, 12.dp)
        cardElevation = 8f.dp
        recyclerView {
            matchParentWidth()
            wrapContentHeight()
            val hourlyAdapter = HourlyForecastAdapter()
            bind(hourlyForecast) {
                hourlyAdapter.submitList(it)
            }
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = hourlyAdapter
        }
    }