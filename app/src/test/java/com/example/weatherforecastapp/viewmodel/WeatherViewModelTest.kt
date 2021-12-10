package com.example.weatherforecastapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.weatherforecastapp.models.WeatherRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.internal.assertThreadHoldsLock
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock


//class WeatherViewModelTest {
//
//    @get:Rule
//    val rule = InstantTaskExecutorRule()
//
//
//    @get:Rule
//    val coroutinesDispatcherRule = CoroutineDispatcherRule()
//
//    private lateinit var  viewModel: WeatherViewModel
//    private lateinit var  repository: WeatherRepository
//    private lateinit var observer: Observer<>
//
//    @Before
//    fun setup() {
//        repository = mock()
//        viewModel = WeatherViewModel(repository)
//        observer = mock()
//        viewModel.getListOfWeather("Minsk").observeForever(observer)
//    }
//
//    @ExperimentalCoroutinesApi
//    @Test
//    fun  getListOfWeather() = coroutinesDispatcherRule.testDispatcher.runBlockingTest {
//        viewModel.getListOfWeather("Minsk")
//        Hamcrest
//    }
//}




//}