package org.kshchegolev.weatherforecast.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.kshchegolev.weatherforecast.presentation.HourlyForecastAdapter
import org.kshchegolev.weatherforecast.R
import org.kshchegolev.weatherforecast.domain.models.HourlyForecast

class MainActivity : AppCompatActivity() {

    private lateinit var hourlyForecastAdapter: HourlyForecastAdapter

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
    }

    private fun initializeRecyclerView() {
        val hourlyRecyclerView = findViewById<RecyclerView>(R.id.hourly_recycleView)
        hourlyForecastAdapter = HourlyForecastAdapter()
        hourlyRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = hourlyForecastAdapter
        }
        hourlyForecastAdapter.submitList(
            List(20) {
                HourlyForecast(
                    hour = "$it",
                    temp = "29",
                    timestamp = it.toLong(),
                )
            }
        )
    }
}