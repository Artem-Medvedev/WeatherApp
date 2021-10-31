package com.example.weatherforecastapp.views

import android.Manifest
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
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.TodayFragmentBinding
import com.example.weatherforecastapp.entity.WeatherObject
import com.example.weatherforecastapp.viewmodel.WeatherFactory
import com.example.weatherforecastapp.viewmodel.WeatherViewModel
import com.google.android.gms.location.LocationServices
import java.util.*

class TodayFragment:Fragment() {
    private var currentLoc: String? = null
    private var _binding: TodayFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =TodayFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root


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

                weatherViewModel = ViewModelProvider(this, WeatherFactory(currentLoc)).get(
                    WeatherViewModel::class.java)

                weatherViewModel.items.observe(viewLifecycleOwner, androidx.lifecycle.Observer{
                    binding.cityNameTextView.text = currentLoc
                    binding.weatherDescTextView.text = it[0].desc
                    binding.mainTempTextView.text = it[0].temp.toInt().toString() + "\u2103"
                    binding.humidityTextView.text = it[0].humidity.toInt().toString()
                    binding.speedTextView.text = it[0].speed.toInt().toString()
                    binding.pressureTextView.text = it[0].pressure.toInt().toString()

                    if(it[0].desc=="Clear"){
                        binding.imageView.load(R.drawable.ic_light_mode_black_24dp)
                    }
                    if(it[0].desc=="Clouds"){
                        binding.imageView.load(R.drawable.cloud)
                    }
                    if(it[0].desc=="Rain"||it[0].desc=="Thunderstorm"){
                        binding.imageView.load(R.drawable.raining)
                    }


                    val yourResults="""Today's weather is:
                  ${binding.cityNameTextView.text}
                   Temperature: ${binding.mainTempTextView.text}
                   Wind speed: ${binding.speedTextView.text}
                   Pressure: ${binding.pressureTextView.text}
                   Humidity: ${binding.humidityTextView.text}
                    """.trimMargin()
                    binding.imageButton.setOnClickListener {
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            type = "plain/text"
                            putExtra(Intent.EXTRA_SUBJECT, "Your Weather")
                            putExtra(Intent.EXTRA_TEXT, yourResults)
                        }
                        startActivity(sendIntent)
                    }

                })


            }

        } else{
            ActivityCompat.requestPermissions(activity as MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),44)
        }
        return root

    }


}