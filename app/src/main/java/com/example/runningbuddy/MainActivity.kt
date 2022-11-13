package com.example.runningbuddy

import android.graphics.Color
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.runningbuddy.ui.settings.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object {
        var TOKEN = ""
        var SRVURL = "https://projet3-running-buddy.herokuapp.com"
        var userId: Int = 0
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.top_nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        
        // Handle item selection
        return when (item.itemId) {
            R.id.settings -> {
//                Navigation.findNavController().navigate(R.id.setting)
                true
            }
            R.id.friends -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}