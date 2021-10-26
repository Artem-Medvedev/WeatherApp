package com.example.weatherforecastapp.contract

import com.example.weatherforecastapp.entity.WeatherObject
import io.reactivex.Observable


interface MainContract {
    interface Model{
        fun getListOfWeather(): Observable<List<WeatherObject>>
    }
}