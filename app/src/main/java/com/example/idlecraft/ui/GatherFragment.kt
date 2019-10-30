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

        // UI Initialization
        updateItemText(textSticks, sticksItem)

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
                    updateItemText(textSticks, sticksItem);
                }
            }).start()
        }
        return view
    }
}