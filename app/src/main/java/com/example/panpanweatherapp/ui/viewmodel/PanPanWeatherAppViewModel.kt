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
        val date = Date(it.time * 1000L)
        SimpleDateFormat("MMMM dd", Locale("id")).format(date)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val formatedTime = weather.map {
        val date = Date(it.time * 1000L)
        SimpleDateFormat("HH:mm a", Locale("id")).format(date)
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
        SimpleDateFormat("HH:mm a", Locale("id")).format(Date(it.sunriseTime * 1000L))
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val sunsetTime = weather.map {
        SimpleDateFormat("HH:mm a", Locale("id")).format(Date(it.sunsetTime * 1000L))
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "")
}