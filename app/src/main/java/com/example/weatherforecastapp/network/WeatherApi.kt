package com.example.weatherforecastapp.network

import com.example.weatherforecastapp.entity.ApiData
import io.reactivex.Observable
import retrofit2.http.GET

interface WeatherApi {
    @GET("data/2.5/forecast?q=London&units=metric&appid=8d7e15b4b9a5ccf449b71cad2c4ec29a")
    fun getListOfWeather(): Observable<ApiData>
}