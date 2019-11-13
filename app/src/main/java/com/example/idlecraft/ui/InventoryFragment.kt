package com.example.idlecraft.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.idlecraft.R
import com.example.idlecraft.MainActivity
import com.example.idlecraft.mechanics.Item
import kotlinx.android.synthetic.main.fragment_inventory.view.*

class InventoryFragment : Fragment() {
    private var act: MainActivity? = null

    // updateItemText: Update the TextView to display min/max quantity for an item and the
    // item's name.
    private fun updateItemText(text: TextView, item: Item) {
        text.text = "  " + item.count.toString() + "/" + item.max.toString() + "\n  " + item.name
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
        act = activity as MainActivity

        // Setup preferences (for save/load state)
        val prefs: SharedPreferences = act!!.getApplicationContext().getSharedPreferences("ICSave", 0)
        val editor: SharedPreferences.Editor = prefs.edit()

        // General Inventory Setup
        val view = inflater.inflate(R.layout.fragment_inventory, container, false)
        val inv = act!!.inventory
        val textMoney = view.text_inv_money

        //==========================================================================================
        // Sticks Variables and Button Listeners
        //==========================================================================================

        val textSticks = view.text_inv_sticks_quan
        val sticksItem = inv.getItemByName("sticks")
        var sticksTradeQuan = 1
        view.text_inv_sticks_trade_quan.text = sticksTradeQuan.toString()

        // Sell Sticks Button
        view.button_inv_sticks_sell.setOnClickListener() {
            if(sticksItem.count >= sticksTradeQuan) {
                inv.increaseMoney(sticksItem.sellValue * sticksTradeQuan)
                sticksItem.decreaseCount(sticksTradeQuan)
            }
            else {
                inv.increaseMoney(sticksItem.sellValue * sticksItem.count)
                sticksItem.decreaseCount(sticksItem.count)
            }
        }
        // Buy Sticks Button
        view.button_inv_sticks_buy.setOnClickListener() {
            if(inv.money >= (sticksItem.buyValue * sticksTradeQuan)
                         && (sticksItem.count + sticksTradeQuan) <= sticksItem.max) {
                sticksItem.increaseCount(sticksTradeQuan)
                inv.decreaseMoney(sticksItem.buyValue * sticksTradeQuan)
            }
        }

        // Sticks Plus Button (increments the amount of sticks to buy/sell)
        view.button_inv_sticks_plus.setOnClickListener() {
            if (sticksTradeQuan < sticksItem.max) sticksTradeQuan += 1
            view.text_inv_sticks_trade_quan.text = sticksTradeQuan.toString()
        }
        // Sticks Minus Button (decrements the amount of sticks to buy/sell)
        view.button_inv_sticks_minus.setOnClickListener() {
            if (sticksTradeQuan > 1) sticksTradeQuan -= 1
            view.text_inv_sticks_trade_quan.text = sticksTradeQuan.toString()
        }

        view.button_save_state.setOnClickListener {
            val iterator = inv.items.iterator()
            iterator.forEach {
                val keyName = "item_" + it.name
                editor.putInt(keyName, it.count)
            }
            editor.putInt("money", inv.money)
            editor.commit()
        }

        view.button_load_state.setOnClickListener {
            val iterator = inv.items.iterator()
            iterator.forEach {
                val keyName = "item_" + it.name
                it.count = prefs.getInt(keyName, -1)
            }
            inv.money = prefs.getInt("money", -1)
        }

        //==========================================================================================
        // Constant thread to update all text values
        //==========================================================================================

        Thread(Runnable {
            while(true) {
                updateMoneyText(textMoney, inv.money)
                updateItemText(textSticks, sticksItem)
                updateMoneyText(view.text_inv_sticks_sell_price, sticksItem.sellValue)
                updateMoneyText(view.text_inv_sticks_buy_price, sticksItem.buyValue)
            }
        }).start()

        return view
    }
}