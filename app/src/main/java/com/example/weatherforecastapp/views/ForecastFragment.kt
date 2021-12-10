package com.example.weatherforecastapp.views

import android.Manifest
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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecastapp.adapter.ParentAdapter
import com.example.weatherforecastapp.databinding.ForecastFragmentBinding
import com.example.weatherforecastapp.viewmodel.WeatherFactory
import com.example.weatherforecastapp.viewmodel.WeatherViewModel
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ForecastFragment : Fragment() {

    private var currentLoc: String? = null
    private  val weatherViewModel by viewModels<WeatherViewModel>()
    private var _binding: ForecastFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ForecastFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val adapter = ParentAdapter()
        binding.listRecyclerView.adapter = adapter
        binding.listRecyclerView.layoutManager = LinearLayoutManager(activity as MainActivity)

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



                weatherViewModel.getListOfWeather(currentLoc).observe(
                    viewLifecycleOwner,
                    { weather ->
                        val weatherFor = weather.groupBy { it.date }
                        Log.d("First", weatherFor.toString())

                        adapter.setData(weatherFor)


                    })
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
