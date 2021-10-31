package com.example.weatherforecastapp.viewmodel

import androidx.lifecycle.*
import com.example.weatherforecastapp.entity.WeatherObject
import com.example.weatherforecastapp.models.WeatherApiImpl
import kotlinx.coroutines.launch

class WeatherViewModel(currentLoc: String?) : ViewModel(){

    private val _items = MutableLiveData<List<WeatherObject>>()
    val items: LiveData<List<WeatherObject>> get() = _items

    init {
        viewModelScope.launch {
            _items.value = WeatherApiImpl.getListOfWeather(currentLoc)
        }
    }
}