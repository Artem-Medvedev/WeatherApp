package com.example.weatherforecastapp.contract

import android.content.Context
import com.example.weatherforecastapp.entity.WeatherObject
import io.reactivex.Observable


interface MainContract {
    interface Model{
        fun getListOfWeather(currentLoc: String?): Observable<List<WeatherObject>>
    }
}