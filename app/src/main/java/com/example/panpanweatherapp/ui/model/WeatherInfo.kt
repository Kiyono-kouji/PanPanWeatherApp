package com.example.panpanweatherapp.ui.model

data class WeatherInfo(
    val cityName: String = "",
    val time: Int = 0,
    val weatherIconId: String = "",
    val weatherConditions: String = "",
    val temperature: Double = 0.0,
    val humidity: Int = 0,
    val windSpeed: Double = 0.0,
    val feelsLikeTemperature: Double = 0.0,
    val rainFall: Double? = null,
    val pressure: Int = 0,
    val clouds: Int = 0,
    val sunriseTime: Int = 0,
    val sunsetTime: Int = 0,
    val errorOccured: Boolean = false,
    val errorResponse: String? = null
)
