package org.kshchegolev.weatherforecast.presentation.views.components

import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.lifecycle.LiveData
import org.kshchegolev.weatherforecast.R
import org.kshchegolev.weatherforecast.presentation.models.CurrentWeatherUi
import org.kshchegolev.weatherforecast.presentation.models.DailyForecastUi
import org.kshchegolev.weatherforecast.presentation.models.HourlyForecastUi
import org.kshchegolev.weatherforecast.presentation.enums.Panel
import org.kshchegolev.weatherforecast.presentation.models.UiState
import org.kshchegolev.weatherforecast.presentation.views.components.screenpanels.contentPanel
import org.kshchegolev.weatherforecast.presentation.views.components.screenpanels.errorPanel
import org.kshchegolev.weatherforecast.presentation.views.components.screenpanels.loadingPanel
import org.kshchegolev.weatherforecast.presentation.views.dsl.bind
import org.kshchegolev.weatherforecast.presentation.views.dsl.dp
import org.kshchegolev.weatherforecast.presentation.views.dsl.frameLayout
import org.kshchegolev.weatherforecast.presentation.views.dsl.matchParentHeight
import org.kshchegolev.weatherforecast.presentation.views.dsl.matchParentWidth
import org.kshchegolev.weatherforecast.presentation.views.dsl.scrollView
import org.kshchegolev.weatherforecast.presentation.views.dsl.scrollingViewBehavior
import org.kshchegolev.weatherforecast.presentation.views.dsl.showSnackbar
import org.kshchegolev.weatherforecast.presentation.views.dsl.swipeRefreshLayout

internal fun ViewGroup.weatherScreen(
    state: LiveData<UiState>,
    showPanel: LiveData<Panel>,
    title: LiveData<String>,
    currentWeather: LiveData<CurrentWeatherUi>,
    hourlyForecast: LiveData<List<HourlyForecastUi>>,
    dailyForecast: LiveData<List<DailyForecastUi>>,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    onSnackbarShown: () -> Unit
) {
    matchParentWidth()
    matchParentHeight()
    weatherAppBar(titleText = title)
    setupSnackBar(state = state, onSnackbarShown = onSnackbarShown)
    swipeRefreshLayout {
        matchParentWidth()
        matchParentHeight()
        scrollingViewBehavior()
        setColorSchemeColors(context.getColor(R.color.md_theme_primary))
        bind(state) { isRefreshing = it.isRefreshing }
        bind(showPanel) { isEnabled = it == Panel.Content }
        setOnRefreshListener { onRefresh() }
        scrollView {
            matchParentWidth()
            matchParentHeight()
            clipChildren = false
            clipToPadding = false
            frameLayout {
                matchParentWidth()
                matchParentHeight()
                setPadding(16.dp)
                clipChildren = false
                clipToPadding = false
                contentPanel(
                    showPanel = showPanel,
                    currentWeather = currentWeather,
                    hourlyForecast = hourlyForecast,
                    dailyForecast = dailyForecast
                )
                loadingPanel(showPanel = showPanel)
                errorPanel(
                    showPanel = showPanel,
                    onRetry = { onRetry() })
            }
        }
    }
}

private fun ViewGroup.setupSnackBar(
    state: LiveData<UiState>,
    onSnackbarShown: () -> Unit
) {
    bind(state) {
        if (it.shouldShowErrorSnackBar) {
            showSnackbar(context.getString(R.string.error_msg))
            onSnackbarShown()
        }
    }
}
