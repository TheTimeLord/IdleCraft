package com.example.idlecraft

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        // remove craft and shop tab initially
        navView.getMenu().findItem(R.id.navigation_craft).isVisible = false
        navView.getMenu().findItem(R.id.navigation_shop).isVisible = false

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_gather, R.id.navigation_craft, R.id.navigation_shop
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun showCraftBtn (v: View) {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.getMenu().findItem(R.id.navigation_craft).isVisible = true
        v.findViewById<Button>(R.id.show_craft_btn).isEnabled = false
    }

    fun showShopBtn (v: View) {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.getMenu().findItem(R.id.navigation_shop).isVisible = true
        v.findViewById<Button>(R.id.show_shop_btn).isEnabled = false
    }
}
