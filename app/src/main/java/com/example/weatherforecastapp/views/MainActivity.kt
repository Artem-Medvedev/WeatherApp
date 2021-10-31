package com.example.weatherforecastapp.views

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.ActivityMainBinding
import com.example.weatherforecastapp.databinding.InternetErrorBinding
import com.example.weatherforecastapp.navigation.KeepStateNavigator

class MainActivity : AppCompatActivity() {
    private lateinit var binding1: ActivityMainBinding
    private lateinit var binding2: InternetErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding1 = ActivityMainBinding.inflate(layoutInflater)
        binding2 = InternetErrorBinding.inflate(layoutInflater)

        if(isConnectedToInternet()){
            setContentView(binding1.root)
            val navController = findNavController(R.id.nav_host_fragment_activity_main)
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)!!
            val navigator = KeepStateNavigator(this,navHostFragment.childFragmentManager, R.id.nav_host_fragment_activity_main)
            navController.navigatorProvider.addNavigator(navigator)
            navController.setGraph(R.navigation.nav_graph)
            binding1.navView.setupWithNavController(navController)
        } else {
            setContentView(binding2.root)
            binding2.button.setOnClickListener {
                if(isConnectedToInternet()){
                    setContentView(binding1.root)
                    val navController = findNavController(R.id.nav_host_fragment_activity_main)
                    val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)!!
                    val navigator = KeepStateNavigator(this,navHostFragment.childFragmentManager, R.id.nav_host_fragment_activity_main)
                    navController.navigatorProvider.addNavigator(navigator)
                    navController.setGraph(R.navigation.nav_graph)
                    binding1.navView.setupWithNavController(navController)
                }
            }
        }
        window.statusBarColor = ContextCompat.getColor(this,R.color.light_blue)


    }

    private fun isConnectedToInternet(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetwork != null
    }

}