package org.kshchegolev.weatherforecast.presentation

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import coil3.load
import com.google.android.material.R.style
import dagger.hilt.android.AndroidEntryPoint
import org.kshchegolev.weatherforecast.R
import org.kshchegolev.weatherforecast.presentation.views.dsl.alwaysShow
import org.kshchegolev.weatherforecast.presentation.views.dsl.appBarLayout
import org.kshchegolev.weatherforecast.presentation.views.dsl.bind
import org.kshchegolev.weatherforecast.presentation.views.dsl.button
import org.kshchegolev.weatherforecast.presentation.views.dsl.cardView
import org.kshchegolev.weatherforecast.presentation.views.dsl.dp
import org.kshchegolev.weatherforecast.presentation.views.dsl.frameLayout
import org.kshchegolev.weatherforecast.presentation.views.dsl.height
import org.kshchegolev.weatherforecast.presentation.views.dsl.horizontalLayout
import org.kshchegolev.weatherforecast.presentation.views.dsl.imageView
import org.kshchegolev.weatherforecast.presentation.views.dsl.matchParentHeight
import org.kshchegolev.weatherforecast.presentation.views.dsl.matchParentWidth
import org.kshchegolev.weatherforecast.presentation.views.dsl.progress
import org.kshchegolev.weatherforecast.presentation.views.dsl.recyclerView
import org.kshchegolev.weatherforecast.presentation.views.dsl.scrollView
import org.kshchegolev.weatherforecast.presentation.views.dsl.scrollingViewBehavior
import org.kshchegolev.weatherforecast.presentation.views.dsl.setContent
import org.kshchegolev.weatherforecast.presentation.views.dsl.showSnackbar
import org.kshchegolev.weatherforecast.presentation.views.dsl.size
import org.kshchegolev.weatherforecast.presentation.views.dsl.space
import org.kshchegolev.weatherforecast.presentation.views.dsl.swipeRefreshLayout
import org.kshchegolev.weatherforecast.presentation.views.dsl.textView
import org.kshchegolev.weatherforecast.presentation.views.dsl.toolbar
import org.kshchegolev.weatherforecast.presentation.views.dsl.verticalLayout
import org.kshchegolev.weatherforecast.presentation.views.dsl.weight
import org.kshchegolev.weatherforecast.presentation.views.dsl.wrapContentHeight
import org.kshchegolev.weatherforecast.presentation.views.dsl.wrapContentWidth

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<ForecastViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            matchParentWidth()
            matchParentHeight()
            appBar()
            bind(viewModel.state) {
                if (it.shouldShowErrorSnackBar) {
                    showSnackbar(getString(R.string.error_msg))
                    viewModel.snackBarShown()
                }
            }
            swipeRefreshLayout {
                matchParentWidth()
                matchParentHeight()
                scrollingViewBehavior()
                setColorSchemeColors(getColor(R.color.md_theme_primary))
                bind(viewModel.state) {
                    isRefreshing = it.isRefreshing
                }
                bind(viewModel.showPanel) {
                    isEnabled = it == Panel.Content
                }
                setOnRefreshListener {
                    viewModel.refreshData()
                }
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
                        verticalLayout {
                            bind(viewModel.showPanel) {
                                visibility = if (it == Panel.Content)
                                    View.VISIBLE
                                else
                                    View.GONE
                            }
                            bind(viewModel.showPanel) {
                                visibility = if (it == Panel.Content)
                                    View.VISIBLE
                                else
                                    View.GONE
                            }
                            currentWeather()
                            space { size(0.dp, 24.dp) }
                            hourlyForecast()
                            space { size(0.dp, 24.dp) }
                            dailyForecast()
                        }
                        verticalLayout {
                            matchParentWidth()
                            height(200.dp)
                            gravity = Gravity.CENTER
                            bind(viewModel.showPanel) {
                                visibility =
                                    if (it == Panel.Loading) View.VISIBLE else View.GONE
                            }
                            progress {
                                isIndeterminate = true
                            }
                        }
                        verticalLayout {
                            matchParentWidth()
                            matchParentHeight()
                            gravity = Gravity.CENTER
                            bind(viewModel.showPanel) {
                                visibility =
                                    if (it == Panel.Error) View.VISIBLE else View.GONE
                            }
                            textView {
                                wrapContentWidth()
                                wrapContentHeight()
                                setTextAppearance(style.TextAppearance_Material3_BodyLarge)
                                setTextColor(getColor(R.color.md_theme_secondary))
                                text = getString(R.string.error_msg)
                            }
                            space { size(0.dp, 24.dp) }
                            button {
                                wrapContentWidth()
                                wrapContentHeight()
                                setTextAppearance(style.TextAppearance_MaterialComponents_Button)
                                setTextColor(getColor(R.color.md_theme_onPrimary))
                                text = getString(R.string.reload_btn)
                                setOnClickListener { viewModel.loadData() }
                            }
                        }
                    }
                }
            }
        }

        viewModel.initialize()
    }

    private fun ViewGroup.appBar() =
        appBarLayout {
            matchParentWidth()
            wrapContentHeight()
            toolbar {
                matchParentWidth()
                wrapContentHeight()
                alwaysShow()
                bind(viewModel.title) { it ->
                    title = it.ifEmpty { getString(R.string.app_name) }
                }
            }
        }

    private fun ViewGroup.currentWeather() =
        cardView {
            cardElevation = 8f.dp
            setContentPadding(24.dp, 24.dp, 24.dp, 24.dp)
            horizontalLayout {
                textView {
                    wrapContentWidth()
                    wrapContentHeight()
                    setTextAppearance(style.TextAppearance_Material3_DisplayLarge)
                    setTextColor(getColor(R.color.md_theme_primary))
                    bind(viewModel.currentWeather) { weather ->
                        text = weather.temp
                    }
                }
                imageView {
                    size(64.dp, 64.dp)
                    bind(viewModel.currentWeather) { weather ->
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
                    setTextColor(getColor(R.color.md_theme_secondary))
                    bind(viewModel.currentWeather) { weather ->
                        text = getString(R.string.updated_at, weather.updatedAt)
                    }
                }
            }
        }

    private fun ViewGroup.hourlyForecast() =
        cardView {
            setContentPadding(12.dp, 12.dp, 12.dp, 12.dp)
            cardElevation = 8f.dp
            recyclerView {
                matchParentWidth()
                wrapContentHeight()
                val hourlyAdapter = HourlyForecastAdapter()
                bind(viewModel.hourlyForecast) {
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

    private fun ViewGroup.dailyForecast() =
        cardView {
            matchParentWidth()
            wrapContentHeight()
            cardElevation = 8f.dp
            setContentPadding(24.dp, 24.dp, 24.dp, 24.dp)
            verticalLayout {
                matchParentWidth()
                wrapContentHeight()
                bind(viewModel.dailyForecast) { items ->
                    removeAllViews()
                    items.map { forecast ->
                        horizontalLayout {
                            matchParentWidth()
                            wrapContentHeight()
                            gravity = Gravity.CENTER_VERTICAL
                            textView {
                                weight(0.1f)
                                wrapContentHeight()
                                setTextAppearance(style.TextAppearance_Material3_BodyLarge)
                                setTextColor(getColor(R.color.md_theme_primary))
                                text = forecast.day
                            }
                            imageView {
                                size(48.dp, 48.dp)
                                load(forecast.iconUrl)
                            }
                            textView {
                                wrapContentWidth()
                                wrapContentHeight()
                                setTextAppearance(style.TextAppearance_Material3_BodyLarge_Emphasized)
                                setTextColor(getColor(R.color.md_theme_primary))
                                setPadding(12.dp, 0.dp, 0.dp, 0.dp)
                                text = forecast.tempMax
                            }
                            textView {
                                wrapContentHeight()
                                setTextAppearance(style.TextAppearance_Material3_BodyLarge)
                                setTextColor(getColor(R.color.md_theme_secondary))
                                setPadding(12.dp, 0.dp, 0.dp, 0.dp)
                                text = forecast.tempMin
                            }
                        }
                    }
                }
            }
        }
}