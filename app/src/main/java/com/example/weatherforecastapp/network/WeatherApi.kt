package com.example.weatherforecastapp.network

import com.example.weatherforecastapp.entity.ApiData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast")
    suspend fun getListOfWeather(
        @Query("q") city: String?,
        @Query("units") units: String,
        @Query("appid") appid: String
    ): ApiData
}



