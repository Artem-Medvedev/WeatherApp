package com.example.weatherforecastapp.views

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.weatherforecastapp.MyReceiver
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.ActivityMainBinding
import com.example.weatherforecastapp.databinding.InternetErrorBinding
import com.example.weatherforecastapp.navigation.KeepStateNavigator
import com.example.weatherforecastapp.settings.SettingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding1: ActivityMainBinding
    private lateinit var binding2: InternetErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding1 = ActivityMainBinding.inflate(layoutInflater)
        binding2 = InternetErrorBinding.inflate(layoutInflater)


        if (isConnectedToInternet()) {
            setContentView(binding1.root)
            setMainScreen()
        } else {
            setContentView(binding2.root)
            binding2.button.setOnClickListener {
                if (isConnectedToInternet()) {
                    setContentView(binding1.root)
                    setMainScreen()
                }
            }
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.dark_blue)

        createNotificationChannel()
        val intent = Intent(this, MyReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            1000 * 60 * 60 * 24,
            pendingIntent
        )

    }

    override fun onResume() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        if (prefs.getBoolean("switch", false)) {
            Log.d("First", prefs.getBoolean("switch", false).toString())
            setTheme(R.style.Theme_DarkVersion)
        } else {
            setTheme(R.style.Theme_Light)
        }
        super.onResume()
    }

    private fun setMainScreen() {
        if (isConnectedToInternet()) {
            val navController = findNavController(R.id.nav_host_fragment_activity_main)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)!!
            val navigator = KeepStateNavigator(
                this,
                navHostFragment.childFragmentManager,
                R.id.nav_host_fragment_activity_main
            )
            navController.navigatorProvider.addNavigator(navigator)
            navController.setGraph(R.navigation.nav_graph)
            binding1.navView.setupWithNavController(navController)
        }
    }

    private fun isConnectedToInternet(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetwork != null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "notify"
            val description = "Channel for weather"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("notify", name, importance)
            channel.description = description

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

}