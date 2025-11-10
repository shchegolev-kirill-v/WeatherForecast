package org.kshchegolev.weatherforecast.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.kshchegolev.weatherforecast.presentation.views.components.weatherScreen
import org.kshchegolev.weatherforecast.presentation.views.dsl.setContent

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<ForecastViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initViews()
        viewModel.initialize()
    }

    private fun initViews() {
        setContent {
            weatherScreen(
                state = viewModel.state,
                showPanel = viewModel.showPanel,
                title = viewModel.title,
                currentWeather = viewModel.currentWeather,
                hourlyForecast = viewModel.hourlyForecast,
                dailyForecast = viewModel.dailyForecast,
                onRefresh = { viewModel.refreshData() },
                onRetry = { viewModel.loadData() },
                onSnackbarShown = { viewModel.snackBarShown() }
            )
        }
    }
}