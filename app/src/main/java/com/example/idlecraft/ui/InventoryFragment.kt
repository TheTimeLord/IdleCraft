package com.example.idlecraft.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.idlecraft.R
import com.example.idlecraft.MainActivity
import com.example.idlecraft.mechanics.Item
import kotlinx.android.synthetic.main.fragment_inventory.*
import kotlinx.android.synthetic.main.fragment_inventory.view.*

class InventoryFragment : Fragment() {
    private var act: MainActivity? = null

    // updateItemText: Update the TextView to display min/max quantity for an item and the
    // item's name.
    private fun updateItemText(text: TextView, item: Item) {
        text.text = item.count.toString() + "/" + item.max.toString() + "\n" + item.name
    }

    // updateMoneyText: Update the TextView for money to newVal
    private fun updateMoneyText(textMoney: TextView, newVal: Int) {
        textMoney.text = "$" + newVal.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Setup
        val view = inflater.inflate(R.layout.fragment_inventory, container, false)
        act = activity as MainActivity
        val inv = act!!.inventory
        val textMoney = view.text_inv_money_cnt
        val textSticks = view.text_inv_sticks_cnt
        val sticksItem = inv.getItemByName("sticks")

        // UI Initialization
        updateMoneyText(textMoney, inv.money)
        updateItemText(textSticks, sticksItem)

        // Insert fragment code here

        // Sell Sticks Button
        view.button_sell_sticks.setOnClickListener() {
            if(sticksItem.count > 0) {
                sticksItem.decreaseCount(1)
                inv.increaseMoney(3)
                updateMoneyText(textMoney, inv.money)
                updateItemText(textSticks, sticksItem)
            }
        }

        return view
    }
}