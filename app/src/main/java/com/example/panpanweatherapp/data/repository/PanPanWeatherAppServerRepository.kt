package com.example.panpanweatherapp.data.repository

import com.example.panpanweatherapp.data.dto.IconResponse
import com.example.panpanweatherapp.data.service.PanPanWeatherAppServerService
import com.example.panpanweatherapp.ui.model.WeatherInfo

class PanPanWeatherAppServerRepository(private val service: PanPanWeatherAppServerService) {
    suspend fun getWeatherInfo(cityName: String): WeatherInfo {
        val APIReturnValue = service.getCurrentWeather(
            cityName = cityName,
            units = "metric",
            apiKey = "5df49e65cfcda565c339487861fdcbc8"
        ).body()!!
        return WeatherInfo(
            cityName = APIReturnValue.name,
            time = APIReturnValue.dt,
            weatherIconId = APIReturnValue.weather[0].icon,
            weatherConditions = APIReturnValue.weather[0].main,
            temperature = APIReturnValue.main.temp,
            humidity = APIReturnValue.main.humidity,
            windSpeed = APIReturnValue.wind.speed,
            feelsLikeTemperature = APIReturnValue.main.feels_like,
            rainFall = APIReturnValue.rain?.`1h` ?: 0.0,
            pressure = APIReturnValue.main.pressure,
            clouds = APIReturnValue.clouds.all,
            sunriseTime = APIReturnValue.sys.sunrise,
            sunsetTime = APIReturnValue.sys.sunset,
            errorOccured = false,
            errorResponse = null
        )
    }
    fun getWeatherIcon(iconId: String): IconResponse{
        val url = "https://openweathermap.org/img/wn/${iconId}@2x.png"
        return IconResponse(iconId, url)
    }
}