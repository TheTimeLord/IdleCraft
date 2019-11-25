package com.example.idlecraft

import android.content.SharedPreferences
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
    var updateThreadInventory: Boolean = false
    var updateThreadCrafting: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each menu should be considered as
        // top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_gather, R.id.navigation_craft,
                R.id.navigation_shop, R.id.navigation_inventory
            )
        )
        //  setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        loadInv()

        // Autosave thread
        Thread(Runnable {
            try {
                Thread.sleep(10000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            saveInv()
        }).start()
    }


    fun saveInv() {
        val prefs: SharedPreferences = applicationContext.getSharedPreferences("ICSave", 0)
        val editor: SharedPreferences.Editor = prefs.edit()

        // iterates over the inventory and saves the total money as well as the
        // count, max, rate, and unlocked fields for each item
        val iterator = inventory.items.iterator()
        iterator.forEach {
            val keyName = "item_" + it.name
            val countKey = keyName + "_count"
            val maxKey = keyName + "_max"
            val rateKey = keyName + "_rate"
            val unlockedKey = keyName + "_unlocked"
            editor.putInt(countKey, it.count)
            editor.putInt(maxKey, it.max)
            editor.putInt(rateKey, it.rate)
            editor.putBoolean(unlockedKey, it.isUnlocked)
        }
        editor.putInt("money", inventory.money)
        editor.commit()
    }

    fun loadInv() {
        val prefs: SharedPreferences = applicationContext.getSharedPreferences("ICSave", 0)

        // populates inventory with data stored from the last save
        val iterator = inventory.items.iterator()
        iterator.forEach {
            val keyName = "item_" + it.name
            val countKey = keyName + "_count"
            val maxKey = keyName + "_max"
            val rateKey = keyName + "_rate"
            val unlockedKey = keyName + "_unlocked"
            it.count = prefs.getInt(countKey, 0)
            it.max = prefs.getInt(maxKey, 10)
            it.rate = prefs.getInt(rateKey, 1)
            it.isUnlocked = prefs.getBoolean(unlockedKey, true)
        }
        inventory.money = prefs.getInt("money", 0)
    }
}
