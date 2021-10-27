package com.example.weatherforecastapp.models

import com.example.weatherforecastapp.contract.MainContract
import com.example.weatherforecastapp.entity.WeatherObject
import com.example.weatherforecastapp.network.WeatherApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object WeatherApiImpl:MainContract.Model {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .build()

    private val weatherService = retrofit.create(WeatherApi::class.java)


    override fun getListOfWeather(currentLoc: String?): Observable<List<WeatherObject>> {


        return   weatherService.getListOfWeather(currentLoc,"metric","e486af048708fe62da581f394970a86c")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMapIterable { apiData -> apiData.weatherMeta }
            .map {
                WeatherObject(
                    it.main.temp,
                    it.weather[0].desc,
                    it.date,
                    it.main.pressure,
                    it.main.humidity,
                    it.wind.speed
                )
            }
            .toList()
            .toObservable()

    }
}