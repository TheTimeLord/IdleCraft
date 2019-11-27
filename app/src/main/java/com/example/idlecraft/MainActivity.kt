//==================================================================================================
// MainActivity.kt
//
// Authors: Brian Andrus, Eduardo Zamora, Nathan Lakritz, Saar Sayfan, Travis Kerns
//
// Description: The code in MainActivity is where the flow of control begins. It is responsible
// for setting up the fragments for each tab and loading/saving player data to persistent storage
// on the user's device. MainActivity is also responsible for handling automated gathering via
// multithreading.
//==================================================================================================
package com.example.idlecraft

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
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
    // Member Fields
    var inventory = Inventory()
    var updateThreadInventory: Boolean = false
    var updateThreadCrafting: Boolean = false
    val gatheringSpeed: Long = 2500
    val autosaveSpeed: Long = 10000

    //==============================================================================================
    // onCreate: Called as soon as the application launches.
    //==============================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        AppBarConfiguration(
            setOf(
                R.id.navigation_gather, R.id.navigation_craft,
                R.id.navigation_shop, R.id.navigation_inventory
            )
        )

        // Load player data. Thread autosaves at a set time interval.
        loadInv()
        Thread(Runnable {
            try { Thread.sleep(autosaveSpeed) }
            catch (e: InterruptedException) { e.printStackTrace() }
            saveInv()
        }).start()

        autoGather()
    }

    //==============================================================================================
    // loadInv: Loads the player's saved Inventory state locally from persistent storage. This is
    // called upon opening the game.
    //==============================================================================================
    private fun loadInv() {
        val prefs: SharedPreferences = applicationContext.getSharedPreferences("ICSave", 0)

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

    //==============================================================================================
    // saveInv: Saves the player's Inventory state locally to persistent storage.
    //==============================================================================================
    fun saveInv() {
        val prefs: SharedPreferences = applicationContext.getSharedPreferences("ICSave", 0)
        val editor: SharedPreferences.Editor = prefs.edit()
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

    //==============================================================================================
    // setProgress: Sets the progress for a ProgressBar view component.
    //==============================================================================================
    private fun setProgress(progress: Int, itemName: String, fragmentName: String) {
        val rootView = window.decorView

        val progressBar = rootView.findViewById<ProgressBar>(
            resources.getIdentifier(
                "progress_${fragmentName}_${itemName}",
                "id",
                "com.example.idlecraft"
            )
        )
        if (progressBar != null) {
            progressBar.progress = progress
        }

    }

    //==============================================================================================
    // autoGather: Starts a thread to automate gathering for upgraded items.
    //==============================================================================================
    private fun autoGather() {
        var progress = 0

        // Thread automates item gathering for gathering items that have been upgraded. In other
        // words, automated gathering won't take place unless the Rate for an item is greater than 1.
        Thread(Runnable {
            while (true) {
                try { Thread.sleep(gatheringSpeed / 100) }
                catch (e: InterruptedException) { e.printStackTrace() }
                progress += 1
                if (progress > 100) progress = 0

                inventory.items.forEach {
                    if (it.rate > 1) {
                        // update progress bar to reflect progress if it exists in the view
                        runOnUiThread {
                            // if the item is at max count, leave progress at 100
                            setProgress(if (it.count < it.max) progress else 100, it.name, "gath")
                        }

                        // update inventory at 100%
                        if (progress == 100) {
                            val newCount = it.count + it.rate
                            it.count = if (newCount > it.max) it.max else newCount
                        }
                    }
                }
            }
        }).start()
    }

    //==============================================================================================
    // startGatherProgress: Starts a thread to handle the gathering of an item and the animation
    //                      of the progress bar.
    //==============================================================================================
    fun startGatherProgress(itemName: String) {
        val item = inventory.getItemByName(itemName)

        Thread(Runnable {
            for (progress in 1..100) {
                runOnUiThread {
                    setProgress(progress, itemName, "gath")
                }
                try { Thread.sleep(gatheringSpeed / 100) }
                catch (e: InterruptedException) { e.printStackTrace() }
            }
            if (item.count + item.rate <= item.max) {
                item.increaseCount(item.rate)
            } else {
                item.count = item.max
            }
            runOnUiThread {
                setProgress(0, itemName, "gath")
            }
        }).start()
    }
}
