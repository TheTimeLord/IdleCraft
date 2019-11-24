package com.example.idlecraft.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.idlecraft.MainActivity
import com.example.idlecraft.R
import com.example.idlecraft.mechanics.Inventory
import com.example.idlecraft.mechanics.Item
import kotlinx.android.synthetic.main.fragment_gather.view.*

class GatherFragment : Fragment() {
    private var act: MainActivity? = null
    private lateinit var inv: Inventory

    // updateItemText: Update the TextView for an item to display its current count.
    private fun updateItemText(text: TextView, item : Item) {
        text.text = item.count.toString() + "/" + item.max.toString()
    }
    // updateItemRateText: Update the TextView for an item to display its gathering rate.
    private fun updateItemRateText(text: TextView, item : Item) {
        text.text = " x" + item.rate + " " + item.name
    }

    private fun setupGatherItemListeners(v: View, itemName: String) {
        val item = inv.getItemByName(itemName)
        val pkg = "com.example.idlecraft"

        val itemQuantityString = "text_gath_${itemName}_quantity"
        val itemRateString = "text_gath_${itemName}_rate"
        val progressBarString = "progress_gath_${itemName}"
        val gatherButtonString = "button_gath_${itemName}"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //==========================================================================================
        // Item and UI setup
        //==========================================================================================

        val view = inflater.inflate(R.layout.fragment_gather, container, false)
        act = activity as MainActivity
        act!!.saveInv()
        inv = act!!.inventory

        // Sticks Item and UI setup
        val textSticks = view.text_gath_sticks_quantity
        val textSticksRate = view.text_gath_sticks_rate
        val progressBar = view.progress_gath_sticks
        val sticksItem = inv.getItemByName("sticks")
        updateItemText(textSticks, sticksItem)
        updateItemRateText(textSticksRate, sticksItem)

        // Rocks Item and UI setup
        val textRocks = view.text_gath_rocks_quantity
        val textRocksRate = view.text_gath_rocks_rate
        val progressBarRocks = view.progress_gath_rocks
        val rocksItem = inv.getItemByName("rocks")
        updateItemText(textRocks, rocksItem)
        updateItemRateText(textRocksRate, rocksItem)

        // Hide Item and UI setup
        val textHide = view.text_gath_hide_quantity
        val textHideRate = view.text_gath_hide_rate
        val progressBarHide = view.progress_gath_hide
        val hideItem = inv.getItemByName("hide")
        updateItemText(textHide, hideItem)
        updateItemRateText(textHideRate, hideItem)

        // Clay Item and UI setup
        val textClay = view.text_gath_clay_quantity
        val textClayRate = view.text_gath_clay_rate
        val progressBarClay = view.progress_gath_clay
        val clayItem = inv.getItemByName("clay")
        updateItemText(textClay, clayItem)
        updateItemRateText(textClayRate, clayItem)

        // Metal Item and UI setup
        val textMetal = view.text_gath_metal_quantity
        val textMetalRate = view.text_gath_metal_rate
        val progressBarMetal = view.progress_gath_metal
        val metalItem = inv.getItemByName("metal")
        updateItemText(textMetal, metalItem)
        updateItemRateText(textMetalRate, metalItem)

        // Oil Item and UI setup
        val textOil = view.text_gath_oil_quantity
        val textOilRate = view.text_gath_oil_rate
        val progressBarOil = view.progress_gath_oil
        val oilItem = inv.getItemByName("oil")
        updateItemText(textOil, oilItem)
        updateItemRateText(textOilRate, oilItem)

        // Paper Item and UI setup
        val textPaper = view.text_gath_paper_quantity
        val textPaperRate = view.text_gath_paper_rate
        val progressBarPaper = view.progress_gath_paper
        val paperItem = inv.getItemByName("paper")
        updateItemText(textPaper, paperItem)
        updateItemRateText(textPaperRate, paperItem)


        //==========================================================================================
        // UI Event Listeners
        //==========================================================================================

        // Sticks gathering button listener
        view.button_gath_sticks.setOnClickListener {
            if (progressBar.progress != 0 || sticksItem.count >= sticksItem.max)
                return@setOnClickListener

            // Create a thread that animates the item's progress bar, then increments the
            // item's count.
            Thread(Runnable {
                var progress = 0
                while (progress < progressBar.max) {
                    progress += 1
                    progressBar.progress = progress
                    try { Thread.sleep(6) }
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBar.progress = 0              // Reset progress bar
                sticksItem.count += sticksItem.rate   // Increment number of sticks
                if (sticksItem.count > sticksItem.max) sticksItem.count = sticksItem.max

                // runOnUiThread allows the thread to update UI objects
                activity?.runOnUiThread {
                    updateItemText(textSticks, sticksItem)
                }
            }).start()
        }

        // Rocks gathering button listener
        view.button_gath_rocks.setOnClickListener {
            if (progressBarRocks.progress != 0 || rocksItem.count >= rocksItem.max)
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBarRocks.max) {
                    progress += 1
                    progressBarRocks.progress = progress
                    try { Thread.sleep(9) }
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBarRocks.progress = 0
                rocksItem.count += rocksItem.rate
                if (rocksItem.count > rocksItem.max) rocksItem.count = rocksItem.max
                activity?.runOnUiThread {
                    updateItemText(textRocks, rocksItem)
                }
            }).start()
        }

        // Hide gathering button listener
        view.button_gath_hide.setOnClickListener {
            if (progressBarHide.progress != 0 || hideItem.count >= hideItem.max)
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBarHide.max) {
                    progress += 1
                    progressBarHide.progress = progress
                    try { Thread.sleep(12) }
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBarHide.progress = 0
                hideItem.count += hideItem.rate
                if (hideItem.count > hideItem.max) hideItem.count = hideItem.max
                activity?.runOnUiThread {
                    updateItemText(textHide, hideItem)
                }
            }).start()
        }

        // Clay gathering button listener
        view.button_gath_clay.setOnClickListener {
            if (progressBarClay.progress != 0 || clayItem.count >= clayItem.max)
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBarClay.max) {
                    progress += 1
                    progressBarClay.progress = progress
                    try { Thread.sleep(15) }
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBarClay.progress = 0
                clayItem.count += clayItem.rate
                if (clayItem.count > clayItem.max) clayItem.count = clayItem.max
                activity?.runOnUiThread {
                    updateItemText(textClay, clayItem)
                }
            }).start()
        }

        // Metal gathering button listener
        view.button_gath_metal.setOnClickListener {
            if (progressBarMetal.progress != 0 || metalItem.count >= metalItem.max)
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBarMetal.max) {
                    progress += 1
                    progressBarMetal.progress = progress
                    try { Thread.sleep(18) }
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBarMetal.progress = 0
                metalItem.count += metalItem.rate
                if (metalItem.count > metalItem.max) metalItem.count = metalItem.max
                activity?.runOnUiThread {
                    updateItemText(textMetal, metalItem)
                }
            }).start()
        }

        // Oil gathering button listener
        view.button_gath_oil.setOnClickListener {
            if (progressBarOil.progress != 0 || oilItem.count >= oilItem.max)
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBarOil.max) {
                    progress += 1
                    progressBarOil.progress = progress
                    try { Thread.sleep(21) }
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBarOil.progress = 0
                oilItem.count += oilItem.rate
                if (oilItem.count > oilItem.max) oilItem.count = oilItem.max
                activity?.runOnUiThread {
                    updateItemText(textOil, oilItem)
                }
            }).start()
        }

        // Paper gathering button listener
        view.button_gath_paper.setOnClickListener {
            if (progressBarPaper.progress != 0 || paperItem.count >= paperItem.max)
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBarPaper.max) {
                    progress += 1
                    progressBarPaper.progress = progress
                    try { Thread.sleep(24) }
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBarPaper.progress = 0
                paperItem.count += paperItem.rate
                if (paperItem.count > paperItem.max) paperItem.count = paperItem.max
                activity?.runOnUiThread {
                    updateItemText(textPaper, paperItem)
                }
            }).start()
        }
        return view
    }
}