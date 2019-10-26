package com.example.idlecraft.ui.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.idlecraft.R
import com.example.idlecraft.MainActivity
import kotlinx.android.synthetic.main.fragment_inventory.*
import kotlinx.android.synthetic.main.fragment_inventory.view.*

class InventoryFragment : Fragment() {
    var act: MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inventory, container, false)
        act = activity as MainActivity
        val inv = act!!.inventory
        var textMoney = view.text_inv_money_cnt
        var textSticks = view.text_inv_sticks_cnt
        val sticksItem = inv.getItemByName("sticks")
        var money = inv.getMoney()

        // Initializations
        textMoney.setText("$" + money.toString())
        textSticks.setText(sticksItem.getCount().toString() + " sticks")

        // Insert fragment code here

        /** Sell Sticks Button **/
        view.button_sell_sticks.setOnClickListener() {
            if(sticksItem.getCount() > 0) {
                sticksItem.decreaseCount(1)
                inv.increaseMoney(3)
                money = inv.getMoney()

                text_inv_money_cnt.setText("$" + money.toString())
                textSticks.setText(sticksItem.getCount().toString() + " sticks")
            }
        }

        return view
    }

}