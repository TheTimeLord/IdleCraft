package com.example.idlecraft.ui.gather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.idlecraft.MainActivity
import com.example.idlecraft.R
import kotlinx.android.synthetic.main.fragment_gather.view.*
import com.example.idlecraft.mechanics.*
import kotlinx.android.synthetic.main.fragment_gather.*

class GatherFragment : Fragment() {
    var act: MainActivity? = null

    // setEditText: Set the item quantity inside edit to newVal. The item name
    // is appended to the quantity automatically.
    private fun setEditText(edit: EditText, newVal: Int) {
        val str = edit.text.toString()
        val itemType = str.substring(str.indexOf(" "), str.length)
        edit.setText(newVal.toString() + itemType)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //gatherViewModel = ViewModelProviders.of(this).get(GatherViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_gather, container, false)
        act = activity as MainActivity

        var editSticks = view.edit_g_sticks
        var progressBar = view.progress_g_sticks
        var sticksItem = act!!.inventory.getItemByName("sticks")
        setEditText(editSticks, sticksItem.count)

        // Called when the gather sticks button is clicked. It causes the progress bar to
        // start, and once finished, increments the amount of sticks that the player has.
        view.button_g_sticks.setOnClickListener {
            Log.v("clicked", "button has been clicked")
            // TODO: Try to create the progress bar thread in the main activity so it persists
            // Create a thread that animates the progress bar and ensure that the gather button
            // cannot be clicked while currently gathering.
            if (progressBar.progress != 0) return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBar.max) {
                    progress += 1
                    progressBar.progress = progress
                    try { Thread.sleep(32) } // 3 seconds
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBar.progress = 0  // Reset progress bar
                sticksItem.count += 1     // Increment number of sticks

                // runOnUiThread allows the thread to update UI objects
                activity?.runOnUiThread {
                    setEditText(editSticks, sticksItem.count);
                }
            }).start()
        }
        return view
    }
}