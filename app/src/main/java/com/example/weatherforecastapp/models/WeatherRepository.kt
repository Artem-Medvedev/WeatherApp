package com.example.weatherforecastapp.models

import com.example.weatherforecastapp.entity.WeatherObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val weatherRemoteData: WeatherRemoteData) {
    suspend fun getListOfWeather(currentLoc: String?) : List<WeatherObject> {
        return weatherRemoteData.getListOfWeather(currentLoc)
    }
}