package org.kshchegolev.weatherforecast.presentation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.launch
import org.kshchegolev.weatherforecast.presentation.HourlyForecastAdapter
import org.kshchegolev.weatherforecast.R
import org.kshchegolev.weatherforecast.domain.models.HourlyForecast

class MainActivity : AppCompatActivity() {

    private lateinit var hourlyForecastAdapter: HourlyForecastAdapter

    private val viewModel by viewModels<ForecastViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
                        it.isLoading && it.items.isEmpty() -> {
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


                    hourlyForecastAdapter.submitList(it.items)
                }
            }
        }


        viewModel.initialize()
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