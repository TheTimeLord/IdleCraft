package com.example.idlecraft.ui.gather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.idlecraft.R
import kotlinx.android.synthetic.main.fragment_gather.view.*
import com.example.idlecraft.mechanics.*

class GatherFragment : Fragment() {

    private lateinit var gatherViewModel: GatherViewModel
    val Item = Item()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gatherViewModel = ViewModelProviders.of(this).get(GatherViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_gather, container, false)

        // Called when the gather sticks button is clicked. It causes the progress bar to
        // start, and once finished, increments the amount of sticks that the player has.
        view.button_g_sticks.setOnClickListener {
            // TODO: Delete this code and get the number of sticks from the inventory class instead
            // TODO: of the edittext text.
            // TODO: Turn progress bar thread code into a function with the progress bar and time
            // TODO: as parameters
            // TODO: Increment the counter at the end of the progress bar instead of the beginning
            var progressBar = view.progress_g_sticks

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
                progressBar.progress = 0 // Reset progress bar
            }).start()

            // Update the data in the Item class
            // TODO: Use these variables globally so that other files see the changes
            Item.setCount(Item.getCount() + 1)
            var sticks = Item.getCount()

            // Increment the number of sticks on the GUI.
            val str = view.edit_g_sticks.text.toString()
            val substr = str.substring(0, str.indexOf(" "))
            val incVal = 1 + (substr.toInt())
            val newStr = sticks.toString() + " Sticks"
            view.edit_g_sticks.setText(newStr)
        }
        return view
    }


}