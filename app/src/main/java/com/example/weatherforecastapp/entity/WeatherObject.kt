package com.example.weatherforecastapp.entity

data class WeatherObject(
    val temp: Double,
    val desc: String,
    val date: String,
    val time: String,
    val pressure: Double,
    val humidity: Double,
    val speed: Double
)
