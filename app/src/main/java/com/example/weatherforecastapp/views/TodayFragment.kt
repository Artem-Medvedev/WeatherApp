package com.example.weatherforecastapp.views

import android.Manifest
import android.content.ContentValues
import android.content.Intent
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
import coil.load
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.TodayFragmentBinding
import com.example.weatherforecastapp.entity.WeatherObject
import com.example.weatherforecastapp.models.WeatherApiImpl
import com.google.android.gms.location.LocationServices
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.today_fragment.*
import java.util.*

class TodayFragment:Fragment() {
    private var currentLoc: String? = null
    private var _binding: TodayFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =TodayFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root


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
                            binding.cityNameTextView.text = currentLoc + ", "+ t[0].desc
                            binding.mainTempTextView.text = t[0].temp.toInt().toString() + "\u2103"
                            binding.humidityTextView.text = t[0].humidity.toInt().toString() + "\u0025"
                            binding.speedTextView.text = t[0].speed.toInt().toString() + "km/h"
                            binding.pressureTextView.text = t[0].pressure.toInt().toString() + "mm"

                            if(t[0].desc=="Clear"){
                                binding.imageView.load(R.drawable.ic_light_mode_black_24dp)
                            }
                            if(t[0].desc=="Clouds"){
                                binding.imageView.load(R.drawable.cloud)
                            }
                            if(t[0].desc=="Rain"||t[0].desc=="Thunderstorm"){
                                binding.imageView.load(R.drawable.raining)
                            }
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


        val yourResults="""Today's weather is:
            binding.cityNameTextView.text+
            Temperature: ${binding.mainTempTextView.text}
            Wind speed: ${binding.speedTextView.text}
            Pressure:${binding.pressureTextView.text}
            Humidity${binding.humidityTextView.text}
             """.trimMargin()
        binding.imageButton.load(R.drawable.ic_share_black_24dp)
        binding.imageButton.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "plain/text"
                putExtra(Intent.EXTRA_SUBJECT,"Your Weather")
                putExtra(Intent.EXTRA_TEXT, yourResults)
            }
            startActivity(sendIntent)
        }

        return root

    }

   /* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                            binding.cityNameTextView.text = currentLoc + t[0].desc
                            binding.mainTempTextView.text = t[0].temp.toInt().toString() + "\u2103"
                            binding.humidityTextView.text = t[0].humidity.toString()
                            binding.speedTextView.text = t[0].speed.toString()
                            binding.pressureTextView.text = t[0].pressure.toString()
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
    }*/
}