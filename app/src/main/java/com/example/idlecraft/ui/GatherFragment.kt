//==================================================================================================
// GatherFragment.kt
//
// Authors: Brian Andrus, Eduardo Zamora, Nathan Lakritz, Saar Sayfan, Travis Kerns
//
// Description: This file contains the implementation of a fragment that contains functionality
// for in-game gathering. The fragment is created upon opening and destroyed upon closing. A
// number of button listeners are setup that interact with the underlying data structure that
// contains all player data: Inventory.
//==================================================================================================
package com.example.idlecraft.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.idlecraft.MainActivity
import com.example.idlecraft.R
import com.example.idlecraft.mechanics.Inventory
import com.example.idlecraft.mechanics.Item

class GatherFragment : Fragment() {
    // Private Member Fields
    private var act: MainActivity? = null
    private lateinit var inv: Inventory
    private val pkg = "com.example.idlecraft"

    //==============================================================================================
    // updateQuantityText: Update the TextView for an item to display its current count.
    //==============================================================================================
    private fun updateQuantityText(text: TextView, item : Item) {
        val newText = "${item.count}/${item.max}"
        text.text = newText
    }

    //==============================================================================================
    // updateRateText: Update the TextView for an item to display its gathering rate.
    //==============================================================================================
    private fun updateRateText(text: TextView, item : Item) {
        val newText = " x${item.rate} ${item.name}"
        text.text = newText
    }

    //==============================================================================================
    // setupGatherItemListeners: This procedure sets up and references all UI elements in the
    // gathering fragment for a particular item given its item name as a string. The UI elements are
    // named in a uniform fashion so that they can be easily referenced in such fashion. This
    // essentially eliminates redundancy in the code.
    //==============================================================================================
    private fun setupGatherItemListeners(v: View, itemName: String) {
        val item = inv.getItemByName(itemName)

        // Declare strings to reference a set of UI elements for an item
        val quantityStr    = "text_gath_${itemName}_quantity"
        val rateStr        = "text_gath_${itemName}_rate"
        val progressBarStr = "progress_gath_${itemName}"
        val gathButtonStr  = "button_gath_${itemName}"

        // Use the UI strings to reference each UI element
        val itemQuantity   = v.findViewById<TextView>(resources.getIdentifier(quantityStr, "id", pkg))
        val itemRate       = v.findViewById<TextView>(resources.getIdentifier(rateStr, "id", pkg))
        val progressBar    = v.findViewById<ProgressBar>(resources.getIdentifier(progressBarStr, "id", pkg))
        val gathButton     = v.findViewById<ImageButton>(resources.getIdentifier(gathButtonStr, "id", pkg))

        // Update the UI with current values upon entering the fragment
        updateQuantityText(itemQuantity, item)
        updateRateText(itemRate, item)

        // Called when a gathering button is clicked. A thread animates a progress bar, and once the
        // progress bar is filled, the item is incremented by the gathering rate for this item.
        // An item's quantity cannot exceed its max quantity.
        gathButton.setOnClickListener {
            if (progressBar.progress != 0 || item.count >= item.max)
                return@setOnClickListener

            act!!.startProgress(itemName, "gath", 1)
        }
    }

    //==============================================================================================
    // onCreateView: Called upon fragment creation. It sets up a reference to the global inventory
    // object, then sets up button listeners by calling setupGatherItemListeners for each gathering
    // item.
    //==============================================================================================
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gather, container, false)
        act = activity as MainActivity
        act!!.saveInv()
        inv = act!!.inventory

        // Setup UI elements and button listeners
        val gathItems = arrayOf("sticks", "rocks", "hide", "clay", "metal", "oil", "paper")
        gathItems.forEach {
            setupGatherItemListeners(view, it)
        }

        // Update Thread: activate Shop
        act!!.updateThreadCrafting = false
        act!!.updateThreadInventory = false

        return view
    }
}
