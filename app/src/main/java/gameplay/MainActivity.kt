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
package gameplay

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ProgressBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.idlecraft.R
import mechanics.Inventory

class MainActivity : AppCompatActivity() {
    // Member Fields
    var inventory = Inventory()
    var currentFragment: String = ""
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
                R.id.navigation_gather,
                R.id.navigation_craft,
                R.id.navigation_shop,
                R.id.navigation_inventory
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
                if (progress > 100) progress = 1

                inventory.items.forEach {
                    if (it.rate > 1) {
                        // update progress bar to reflect progress if it exists in the view
                        runOnUiThread {
                            setProgress(if (it.count >= it.max) 0 else progress, it.name, "gath")
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
    // startGatherProgress: Starts a thread to handle the gathering/crafting of an item and the
    //                      animation of the progress bar.
    //==============================================================================================
    fun startProgress(itemName: String, fragmentName: String, amount: Int) {
        val item = inventory.getItemByName(itemName)

        Thread(Runnable {
            for (i in 1..amount) {
                // calculate how many items can be gathered/crafted without exceeding item max
                var itemInc = item.rate
                if (item.max - item.count < item.rate) {
                    itemInc = item.max - item.count
                }

                // consume crafting materials if item is crafted
                if (fragmentName == "craft") {
                    val numCanCraft = inventory.howManyCanCraft(item)
                    if (numCanCraft < itemInc) itemInc = numCanCraft

                    val reqItem1 = inventory.getItemByName(item.req1)
                    val reqItem2 = inventory.getItemByName(item.req2)
                    val reqItem3 = inventory.getItemByName(item.req3)

                    if (reqItem1 != null) reqItem1.decreaseCount(item.reqAmount1 * itemInc)
                    if (reqItem2 != null) reqItem2.decreaseCount(item.reqAmount2 * itemInc)
                    if (reqItem3 != null) reqItem3.decreaseCount(item.reqAmount3 * itemInc)
                }

                for (progress in 1..100) {
                    runOnUiThread {
                        setProgress(progress, itemName, fragmentName)
                    }
                    try { Thread.sleep(gatheringSpeed / 100) }
                    catch (e: InterruptedException) { e.printStackTrace() }
                }

                // increase item count in inventory
                item.increaseCount(itemInc)
            }
            runOnUiThread {
                setProgress(0, itemName, fragmentName)
            }
        }).start()
    }
}
