package com.example.idlecraft.ui.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.idlecraft.R
import com.example.idlecraft.mechanics.*
import com.example.idlecraft.MainActivity
import kotlinx.android.synthetic.main.fragment_gather.view.*
import kotlinx.android.synthetic.main.fragment_inventory.*
import kotlinx.android.synthetic.main.fragment_inventory.view.*

class InventoryFragment : Fragment() {

    private lateinit var inventoryViewModel: InventoryViewModel
    var act: MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inventoryViewModel = ViewModelProviders.of(this).get(InventoryViewModel::class.java)

        // Variables here
        val view = inflater.inflate(R.layout.fragment_inventory, container, false)
        act = activity as MainActivity
        val inv = act!!.inventory
        var textMoney = view.text_money_count
        val sticksItem = inv.getItemByName("sticks")
        var money = inv.getMoney()

        // Initializations
        textMoney.setText("$" + money.toString())

        // Insert fragment code here

        /** Sell Sticks Button **/
        view.button_sell_sticks.setOnClickListener() {
            if(sticksItem.getCount() > 0) {
                sticksItem.decreaseCount(1)
                inv.increaseMoney(3)
                money = inv.getMoney()
                text_money_count.setText("$" + money.toString())
            }
        }

        return view
    }

}