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
import com.example.idlecraft.mechanics.Inventory
import com.example.idlecraft.mechanics.Item
import kotlinx.android.synthetic.main.fragment_gather.view.*

class MainActivity : AppCompatActivity() {

    var inventory = Inventory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        // remove craft and shop tab initially
        navView.getMenu().findItem(R.id.navigation_craft).isVisible = true
        navView.getMenu().findItem(R.id.navigation_shop).isVisible =  true

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_gather, R.id.navigation_craft, R.id.navigation_shop, R.id.navigation_inventory
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        initInventory()
    }

    fun initInventory() {
        val sticks = Item()
        sticks.name = "sticks"
        inventory.addItem(sticks)
    }

    /*
    fun showCraftBtn (v: View) {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.getMenu().findItem(R.id.navigation_craft).isVisible = true
    }

    fun showShopBtn (v: View) {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.getMenu().findItem(R.id.navigation_shop).isVisible = true
        v.findViewById<Button>(R.id.show_shop_btn).isEnabled = false
    }

     */
}
