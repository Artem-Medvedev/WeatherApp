package com.example.weatherforecastapp.views

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.adapter.WeatherListAdapter
import com.example.weatherforecastapp.entity.WeatherObject
import com.example.weatherforecastapp.models.WeatherApiImpl
import com.google.android.gms.location.LocationServices
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.forecast_fragment.*
import java.util.*

class ForecastFragment: Fragment(){

    private   var currentLoc: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = WeatherListAdapter()
        listRecyclerView.adapter = adapter
        listRecyclerView.layoutManager = LinearLayoutManager(activity as MainActivity)


        val api = WeatherApiImpl
        if(ActivityCompat.checkSelfPermission((activity as MainActivity)
                , Manifest.permission.ACCESS_FINE_LOCATION
            )== PackageManager.PERMISSION_GRANTED){
            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity as MainActivity)
            var city: List<Address>

                 fusedLocationProviderClient.lastLocation.addOnSuccessListener { location->
                     val geoCoder = Geocoder(activity as MainActivity, Locale.ENGLISH)
                     city = geoCoder.getFromLocation(location.latitude, location.longitude, 1)
                     currentLoc = city.first().locality
                     Log.d("First",city.first().locality)
                     api.getListOfWeather(currentLoc)
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

        } else{
            ActivityCompat.requestPermissions(activity as MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),44)
        }


    }
    }
