package com.example.runningbuddy

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    // TODO : Prednre ces valeurs la et les mettre dans une class objects constant
    companion object {
        var TOKEN = ""
        var SRVURL = "https://projet3-running-buddy.herokuapp.com"
        var userId: Int = 0
        const val REQUEST_CODE_LOCATION_PERMISSION = 0
        const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
        const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
        const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"

        const val TIMER_UPDATE_INTERVAL = 50L

        const val LOCATION_UPDATE_INTERVAL = 5000L
        const val FASTEST_LOCATION_INTERVAL = 2000L

        const val POLYLINE_COLOR = Color.RED
        const val POLYLINE_WIDTH = 8f
        const val MAP_ZOOM = 15f

        const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
        const val NOTIFICATION_CHANNEL_NAME = "Tracking"
        const val NOTIFICATION_ID = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_enregistrercourse,
                R.id.navigation_profile
            )
        )
        //this.setupActionBarWithNavController(navController, appBarConfiguration)

        val navView: BottomNavigationView = this.findViewById(R.id.nav_view)
        navView.setupWithNavController(navController)
    }
}