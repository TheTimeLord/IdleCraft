package com.example.idlecraft.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.idlecraft.MainActivity
import com.example.idlecraft.R
import com.example.idlecraft.mechanics.Item
import kotlinx.android.synthetic.main.fragment_gather.view.*

class GatherFragment : Fragment() {
    private var act: MainActivity? = null

    // updateItemText: Update the TextView for an item to display its current count.
    private fun updateItemText(text: TextView, item : Item) {
        text.text = "x" + item.count + " " + item.name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Setup
        val view = inflater.inflate(R.layout.fragment_gather, container, false)
        act = activity as MainActivity
        val inv = act!!.inventory
        val textSticks = view.text_gath_sticks
        val progressBar = view.progress_gath_sticks
        val sticksItem = inv.getItemByName("sticks")

        val textRocks = view.text_gath_rocks
        val progressBarRocks = view.progress_gath_rocks
        val rocksItem = inv.getItemByName("rocks")

        val textHide = view.text_gath_hide
        val progressBarHide = view.progress_gath_hide
        val hideItem = inv.getItemByName("hide")

        val textClay = view.text_gath_clay
        val progressBarClay = view.progress_gath_clay
        val clayItem = inv.getItemByName("clay")

        val textMetal = view.text_gath_metal
        val progressBarMetal = view.progress_gath_metal
        val metalItem = inv.getItemByName("metal")

        val textOil = view.text_gath_oil
        val progressBarOil = view.progress_gath_oil
        val oilItem = inv.getItemByName("oil")

        val textPaper = view.text_gath_paper
        val progressBarPaper = view.progress_gath_paper
        val paperItem = inv.getItemByName("paper")

        // UI Initialization
        updateItemText(textSticks, sticksItem)
        updateItemText(textRocks, rocksItem)
        updateItemText(textHide, hideItem)
        updateItemText(textClay, clayItem)
        updateItemText(textMetal, metalItem)
        updateItemText(textOil, oilItem)
        updateItemText(textPaper, paperItem)

        // Called when the gather sticks button is clicked. It causes the progress bar to
        // start, and once finished, increments the amount of sticks that the player has.
        view.button_gath_sticks.setOnClickListener {
            // TODO: Try to create the progress bar thread in the main activity so it persists
            // Ensure that gathering is disabled if the maximum of an item has already been
            // obtained or if gathering is already taking place.
            if (progressBar.progress != 0 || sticksItem.count >= sticksItem.max)
                return@setOnClickListener

            // Create a thread that animates the item's progress bar, then increments the
            // item's count.
            Thread(Runnable {
                var progress = 0
                while (progress < progressBar.max) {
                    progress += 1
                    progressBar.progress = progress
                    try { Thread.sleep(16) } // 3 seconds
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBar.progress = 0     // Reset progress bar
                sticksItem.count += 1        // Increment number of sticks

                // runOnUiThread allows the thread to update UI objects
                activity?.runOnUiThread {
                    updateItemText(textSticks, sticksItem)
                }
            }).start()
        }

        view.button_gath_rocks.setOnClickListener {
            if (progressBarRocks.progress != 0 || rocksItem.count == rocksItem.max)
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBarRocks.max) {
                    progress += 1
                    progressBarRocks.progress = progress
                    try { Thread.sleep(16) }
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBarRocks.progress = 0
                rocksItem.count += 1
                activity?.runOnUiThread {
                    updateItemText(textRocks, rocksItem)
                }
            }).start()
        }

        view.button_gath_hide.setOnClickListener {
            if (progressBarHide.progress != 0 || hideItem.count == hideItem.max)
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBarHide.max) {
                    progress += 1
                    progressBarHide.progress = progress
                    try { Thread.sleep(16) }
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBarHide.progress = 0
                hideItem.count += 1
                activity?.runOnUiThread {
                    updateItemText(textHide, hideItem)
                }
            }).start()
        }

        view.button_gath_clay.setOnClickListener {
            if (progressBarClay.progress != 0 || clayItem.count == clayItem.max)
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBarClay.max) {
                    progress += 1
                    progressBarClay.progress = progress
                    try { Thread.sleep(16) }
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBarClay.progress = 0
                clayItem.count += 1
                activity?.runOnUiThread {
                    updateItemText(textClay, clayItem)
                }
            }).start()
        }

        view.button_gath_metal.setOnClickListener {
            if (progressBarMetal.progress != 0 || metalItem.count == metalItem.max)
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBarMetal.max) {
                    progress += 1
                    progressBarMetal.progress = progress
                    try { Thread.sleep(16) }
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBarMetal.progress = 0
                metalItem.count += 1
                activity?.runOnUiThread {
                    updateItemText(textMetal, metalItem)
                }
            }).start()
        }

        view.button_gath_oil.setOnClickListener {
            if (progressBarOil.progress != 0 || oilItem.count == oilItem.max)
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBarOil.max) {
                    progress += 1
                    progressBarOil.progress = progress
                    try { Thread.sleep(16) }
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBarOil.progress = 0
                oilItem.count += 1
                activity?.runOnUiThread {
                    updateItemText(textOil, oilItem)
                }
            }).start()
        }

        view.button_gath_paper.setOnClickListener {
            if (progressBarPaper.progress != 0 || paperItem.count == paperItem.max)
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBarPaper.max) {
                    progress += 1
                    progressBarPaper.progress = progress
                    try { Thread.sleep(16) }
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBarPaper.progress = 0
                paperItem.count += 1
                activity?.runOnUiThread {
                    updateItemText(textPaper, paperItem)
                }
            }).start()
        }
        return view
    }
}