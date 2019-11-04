package com.example.idlecraft

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.idlecraft.mechanics.Inventory
import com.example.idlecraft.mechanics.Item
import kotlinx.android.synthetic.main.fragment_gather.view.*

class MainActivity : AppCompatActivity() {

    var inventory = Inventory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each menu should be considered as
        // top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_gather,  R.id.navigation_craft,
                R.id.navigation_shop,    R.id.navigation_inventory
            )
        )
        //  setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        initInventory()
    }

    // Initialize items in the player's inventory.
    fun initInventory() {
        inventory.money = 0

        // Create sticks item
        val sticks = Item()
        sticks.name = "sticks"
        sticks.max = 10
        sticks.sellValue = 3;
        sticks.buyValue = 6;
        inventory.addItem(sticks)

        // Create rocks item
        val rocks = Item()
        rocks.name = "rocks"
        rocks.max = 10
        inventory.addItem(rocks)

        // Create hide item
        val hide = Item()
        hide.name = "hide"
        hide.max = 10
        inventory.addItem(hide)

        // Create spear item
        val spear = Item()
        spear.name = "spear"
        spear.max = 10
        spear.req1 = "sticks"
        spear.reqAmount1 = 5
        spear.req2 = "rocks"
        spear.reqAmount2 = 5
        inventory.addItem(spear)
    }

}
