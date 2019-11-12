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
        sticks.sellValue = 1
        sticks.buyValue = 6
        sticks.rate = 3
        inventory.addItem(sticks)

        // Create rocks item
        val rocks = Item()
        rocks.name = "rocks"
        rocks.max = 10
        rocks.rate = 1
        inventory.addItem(rocks)

        // Create hide item
        val hide = Item()
        hide.name = "hide"
        hide.max = 10
        hide.rate = 1
        inventory.addItem(hide)

        // Create clay item
        val clay = Item()
        clay.name = "clay"
        clay.max = 10
        clay.rate = 1
        inventory.addItem(clay)

        // Create metal item
        val metal = Item()
        metal.name = "metal"
        metal.max = 10
        metal.rate = 1
        inventory.addItem(metal)

        // Create oil item
        val oil = Item()
        oil.name = "oil"
        oil.max = 10
        oil.rate = 1
        inventory.addItem(oil)

        // Create paper item
        val paper = Item()
        paper.name = "paper"
        paper.max = 10
        paper.rate = 1
        inventory.addItem(paper)


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
