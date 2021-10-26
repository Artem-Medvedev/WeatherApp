package com.example.weatherforecastapp.views

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.adapter.WeatherListAdapter
import com.example.weatherforecastapp.entity.WeatherObject
import com.example.weatherforecastapp.models.WeatherApiImpl
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.forecast_fragment.*

class ForecastFragment: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
        val adapter = WeatherListAdapter()
        listRecyclerView.adapter = adapter
        listRecyclerView.layoutManager = LinearLayoutManager(activity as MainActivity)

        val api = WeatherApiImpl
        api.getListOfWeather()
            .subscribe(object : Observer<List<WeatherObject>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: List<WeatherObject>) {
                    adapter.setData(t)
                }

                override fun onError(e: Throwable) {
                    Log.e(ContentValues.TAG,"onError: ",e)
                }

                override fun onComplete() {
                }
            })
    }
    }
