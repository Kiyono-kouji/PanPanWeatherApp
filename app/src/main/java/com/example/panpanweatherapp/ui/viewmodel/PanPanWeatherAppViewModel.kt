package com.example.panpanweatherapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panpanweatherapp.R
import com.example.panpanweatherapp.data.container.PanPanWeatherAppServerContainer
import com.example.panpanweatherapp.ui.model.WeatherInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PanPanWeatherAppViewModel : ViewModel(){
    private val _weather = MutableStateFlow(WeatherInfo())
    private val _weatherIcon = MutableStateFlow<String?>(null)

    val weather: StateFlow<WeatherInfo> = _weather
    val weatherIcon: StateFlow<String?> = _weatherIcon

    private fun formatTimestamp(timestamp: Int, pattern: String): String {
        val date = Date(timestamp * 1000L)
        return SimpleDateFormat(pattern, Locale("id")).format(date)
    }

    fun searchCity(cityName: String){
        viewModelScope.launch {
            _weather.value = _weather.value.copy(
                errorOccured = false,
                errorResponse = null
            )
            try {
                val result = PanPanWeatherAppServerContainer().PanPanWeatherAppRepository.getWeatherInfo(cityName)
                _weather.value = result.copy(
                    errorResponse = null,
                    errorOccured = false
                )
                _weatherIcon.value = PanPanWeatherAppServerContainer().PanPanWeatherAppRepository.getWeatherIcon(result.weatherIconId)
            } catch (e : Exception){
                _weather.value = _weather.value.copy(
                    errorResponse = "HTTP 404 Not Found",
                    errorOccured = true,
                )
                _weatherIcon.value = null
            }
        }
    }

    val formatedDate = weather.map {
        formatTimestamp(it.time, "MMMM dd")
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val formatedTime = weather.map {
        formatTimestamp(it.time, "HH:mm a")
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val listWeatherInfo = weather.map {
        listOf(
            listOf("HUMIDITY", "${it.humidity ?: 0}%", R.drawable.icon_humidity),
            listOf("WIND", "${it.windSpeed ?: 0}km/h", R.drawable.icon_wind),
            listOf("FEELS LIKE", "${it.feelsLikeTemperature ?: 0}°", R.drawable.icon_feels_like),
            listOf("RAIN FALL", "${it.rainFall ?: 0} mm", R.drawable.vector_2),
            listOf("PRESSURE", "${it.pressure ?: 0}hPa", R.drawable.devices),
            listOf("CLOUDS", "${it.clouds ?: 0}%", R.drawable.cloud)
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val sunrieTime = weather.map {
        formatTimestamp(it.sunriseTime, "HH:mm a")
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val sunsetTime = weather.map {
        formatTimestamp(it.sunsetTime, "HH:mm a")
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "")
}