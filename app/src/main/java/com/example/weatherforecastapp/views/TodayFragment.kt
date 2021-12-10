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
import androidx.fragment.app.viewModels
import coil.load
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.TodayFragmentBinding
import com.example.weatherforecastapp.settings.SettingActivity
import com.example.weatherforecastapp.viewmodel.WeatherViewModel
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TodayFragment : Fragment() {
    private var currentLoc: String? = null
    private var _binding: TodayFragmentBinding? = null
    private val binding get() = _binding!!
    private val weatherViewModel by viewModels<WeatherViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TodayFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.imageButton2.setOnClickListener {
            val intent = Intent(activity as MainActivity, SettingActivity::class.java)
            startActivity(intent)
        }

        if (ActivityCompat.checkSelfPermission(
                (activity as MainActivity), Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(activity as MainActivity)
            var city: List<Address>

            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                val geoCoder = Geocoder(activity as MainActivity, Locale.ENGLISH)
                city = geoCoder.getFromLocation(location.latitude, location.longitude, 1)
                currentLoc = city.first().locality
                Log.d("First", city.first().locality)

                weatherViewModel.getListOfWeather(currentLoc).observe(viewLifecycleOwner, {
                    binding.apply {
                        cityNameTextView.text = currentLoc
                        weatherDescTextView.text = it[0].desc
                        mainTempTextView.text = it[0].temp.toInt().toString() + "\u2103"
                        humidityTextView.text = it[0].humidity.toInt().toString()
                        speedTextView.text = it[0].speed.toInt().toString()
                        pressureTextView.text = it[0].pressure.toInt().toString()
                    }

                    if (it[0].desc == "Clear") {
                        if (it[0].time.substring(0, 2).toInt() >= 20 || it[0].time.substring(0, 2)
                                .toInt() <= 3
                        ) {
                            binding.imageView.load(R.drawable.moon__1_)
                        } else {
                            binding.imageView.load(R.drawable.sun__2_)
                        }
                    }
                    if (it[0].desc == "Clouds") {
                        if (it[0].time.substring(0, 2).toInt() >= 20 || it[0].time.substring(0, 2)
                                .toInt() <= 3
                        ) {
                            binding.imageView.load(R.drawable.moon)
                        } else {
                            binding.imageView.load(R.drawable.cloud)
                        }
                    }
                    if (it[0].desc == "Rain" || it[0].desc == "Thunderstorm") {
                        binding.imageView.load(R.drawable.raining)
                    }


                    val yourResults = """Today's weather is:
                  ${binding.cityNameTextView.text}
                   Temperature: ${binding.mainTempTextView.text}
                   Wind speed: ${binding.speedTextView.text} Km/h
                   Pressure: ${binding.pressureTextView.text} mm
                   Humidity: ${binding.humidityTextView.text} %
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

        } else {
            ActivityCompat.requestPermissions(
                activity as MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                44
            )
        }
        return root

    }


}