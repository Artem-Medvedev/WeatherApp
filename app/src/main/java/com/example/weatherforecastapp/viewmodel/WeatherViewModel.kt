package com.example.weatherforecastapp.viewmodel

import androidx.lifecycle.*
import com.example.weatherforecastapp.entity.WeatherObject
import com.example.weatherforecastapp.models.WeatherApiImpl
import com.example.weatherforecastapp.models.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {

    private val _items = MutableLiveData<List<WeatherObject>>()

    fun getListOfWeather(currentLoc: String?): LiveData<List<WeatherObject>> {
        //val _items =  MutableLiveData<List<WeatherObject>>()
        viewModelScope.launch {
            _items.value = repository.getListOfWeather(currentLoc)
        }
        return _items
    }

}