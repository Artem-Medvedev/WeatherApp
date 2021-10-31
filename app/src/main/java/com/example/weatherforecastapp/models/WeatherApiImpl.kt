package com.example.weatherforecastapp.models

import com.example.weatherforecastapp.entity.WeatherObject
import com.example.weatherforecastapp.network.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object WeatherApiImpl {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .build()

    private val weatherService = retrofit.create(WeatherApi::class.java)


    suspend fun getListOfWeather(currentLoc: String?): List<WeatherObject> {

        val dtfInput = DateTimeFormatter.ofPattern("u-M-d", Locale.ENGLISH)
        val dtfOutput = DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH)
        return   withContext(Dispatchers.IO){
            weatherService.getListOfWeather(currentLoc,"metric","e486af048708fe62da581f394970a86c")
                .weatherMeta.map {
                    WeatherObject(
                        it.main.temp,
                        it.weather[0].desc,
                        LocalDate.parse(it.date.split(" ")[0], dtfInput).format(dtfOutput),
                        it.date.split(" ")[1],
                        it.main.pressure,
                        it.main.humidity,
                        it.wind.speed
                    )
                }
            }
        }
    }
