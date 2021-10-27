package com.example.weatherforecastapp.network

import com.example.weatherforecastapp.entity.ApiData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast")
    fun getListOfWeather(@Query("q")city:String?,
                         @Query("units")units: String,
                         @Query("appid")appid: String): Observable<ApiData>
}


