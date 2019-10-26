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
import kotlinx.android.synthetic.main.fragment_gather.view.*

class GatherFragment : Fragment() {
    private var act: MainActivity? = null

    // updateItemText: Update the TextView for item quantity to newVal. The item name
    // is appended to the quantity automatically.
    private fun updateItemText(text: TextView, newVal: Int) {
        val str = text.text.toString()
        val itemType = str.substring(str.indexOf(" "), str.length)
        text.text = newVal.toString() + itemType
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
        val textSticks = view.edit_g_sticks
        val progressBar = view.progress_g_sticks
        val sticksItem = inv.getItemByName("sticks")

        // UI Initialization
        updateItemText(textSticks, sticksItem.count)

        // Called when the gather sticks button is clicked. It causes the progress bar to
        // start, and once finished, increments the amount of sticks that the player has.
        view.button_g_sticks.setOnClickListener {
            // TODO: Try to create the progress bar thread in the main activity so it persists
            // Create a thread that animates the progress bar and ensure that the gather button
            // cannot be clicked while currently gathering.
            if (progressBar.progress != 0) return@setOnClickListener
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
                    updateItemText(textSticks, sticksItem.count);
                }
            }).start()
        }
        return view
    }
}