package com.example.weatherforecastapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WeatherFactory(val currentLoc: String?) :
 ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(currentLoc) as T
    }

}