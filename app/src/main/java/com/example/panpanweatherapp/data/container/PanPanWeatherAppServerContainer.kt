package com.example.panpanweatherapp.data.container

import com.example.panpanweatherapp.data.repository.PanPanWeatherAppServerRepository
import com.example.panpanweatherapp.data.service.PanPanWeatherAppServerService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PanPanWeatherAppServerContainer {
    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    private val PanPanWeatherAppService: PanPanWeatherAppServerService by lazy {
        retrofit.create(PanPanWeatherAppServerService::class.java)
    }

    val PanPanWeatherAppRepository: PanPanWeatherAppServerRepository by lazy {
        PanPanWeatherAppServerRepository(PanPanWeatherAppService)
    }
}