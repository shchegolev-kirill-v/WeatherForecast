package org.kshchegolev.weatherforecast.presentation

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.google.android.material.R.style
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.kshchegolev.weatherforecast.R
import org.kshchegolev.weatherforecast.presentation.views.dsl.appBarLayout
import org.kshchegolev.weatherforecast.presentation.views.dsl.bind
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
import org.kshchegolev.weatherforecast.presentation.views.dsl.setContent
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

    private lateinit var hourlyForecastAdapter: HourlyForecastAdapter

    private val viewModel by viewModels<ForecastViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            appBarLayout {
                matchParentWidth()
                wrapContentHeight()
                toolbar {
                    wrapContentHeight()
                    bind(viewModel.title) { it ->
                        title = it.ifEmpty { getString(R.string.app_name) }
                    }
                }
            }
            swipeRefreshLayout {
                matchParentWidth()
                matchParentHeight()
                setColorSchemeColors(getColor(R.color.md_theme_primary))
                scrollView {
                    matchParentWidth()
                    matchParentHeight()
                    clipChildren = false
                    clipToPadding = false
                    frameLayout {
                        matchParentWidth()
                        wrapContentHeight()
                        setPadding(16.dp)
                        clipChildren = false
                        clipToPadding = false
                        verticalLayout {
                            bind(viewModel.state) { state ->
                                visibility = if (state.isLoading) View.GONE else View.VISIBLE
                            }
                            currentWeather()
                            space { size(0.dp, 24.dp) }
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
                            space { size(0.dp, 24.dp) }
                            dailyWeather()
                        }
                        verticalLayout {
                            matchParentWidth()
                            height(200.dp)
                            gravity = Gravity.CENTER_HORIZONTAL
                            bind(viewModel.state) { state ->
                                visibility = if (state.isLoading) View.VISIBLE else View.GONE
                            }
                            progress {
                                isIndeterminate = true
                            }
                        }
                        //TODO to add error state
                    }
                }
            }
        }

        viewModel.initialize()
        return

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeRecyclerView()

        val loadingLayout = findViewById<LinearLayout>(R.id.loading_layout)
        val errorLayout = findViewById<LinearLayout>(R.id.error_layout)
        val contentLayout = findViewById<LinearLayout>(R.id.content_layout)

        val currentTempTextView = findViewById<MaterialTextView>(R.id.current_temp_textview)
        val weatherIconImageView = findViewById<ImageView>(R.id.weather_icon_imageview)
        val updatedAtTextView = findViewById<MaterialTextView>(R.id.updated_at_textview)
        currentTempTextView.text = "29°"
        weatherIconImageView.load("https://cdn.weatherapi.com/weather/64x64/day/122.png")
        updatedAtTextView.text = getString(R.string.updated_at, "12:00")

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when {
                        it.isLoading && it.hourlyForecasts.isEmpty() -> {
                            loadingLayout.visibility = View.VISIBLE
                            errorLayout.visibility = View.GONE
                            contentLayout.visibility = View.GONE
                        }

                        !it.isLoading -> {
                            loadingLayout.visibility = View.GONE
                            errorLayout.visibility = View.GONE
                            contentLayout.visibility = View.VISIBLE
                        }
                    }
                    hourlyForecastAdapter.submitList(it.hourlyForecasts)
                }
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
                        text = weather.updatedAt
                    }
                }
            }
        }

    private fun ViewGroup.dailyWeather() =
        cardView {
            matchParentWidth()
            wrapContentHeight()
            cardElevation = 8f.dp
            setContentPadding(24.dp, 24.dp, 24.dp, 24.dp)
            verticalLayout {
                matchParentWidth()
                wrapContentHeight()
                bind(viewModel.dailyForecast) { items ->
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

    private fun initializeRecyclerView() {
        val hourlyRecyclerView = findViewById<RecyclerView>(R.id.hourly_recycleView)
        hourlyForecastAdapter = HourlyForecastAdapter()
        hourlyRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = hourlyForecastAdapter
        }
    }
}