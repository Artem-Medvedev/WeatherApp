package com.example.weatherforecastapp.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiData(
    @Json(name = "list") val weatherMeta: List<WeatherMeta>
)

@JsonClass(generateAdapter = true)
data class WeatherMeta(
    @Json(name = "main") val main: Main,
    @Json(name = "weather") val weather: List<Weather>,
    @Json(name = "dt_txt") val date: String,
    @Json(name = "wind") val wind: Wind
)

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "temp") val temp: Double,
    @Json(name = "pressure") val pressure: Double,
    @Json(name = "humidity") val humidity: Double,
)

@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "main") val desc: String,
)

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "speed") val speed: Double
)

