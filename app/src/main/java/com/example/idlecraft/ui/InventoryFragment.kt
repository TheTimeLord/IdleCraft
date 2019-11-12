package com.example.idlecraft.ui

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
        text.text = "  " + item.count.toString() + "/" + item.max.toString() + "\n " + item.name
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
        // General Inventory Setup
        val view = inflater.inflate(R.layout.fragment_inventory, container, false)
        act = activity as MainActivity
        act!!.saveInv()
        val inv = act!!.inventory
        val textMoney = view.text_inv_money
        updateMoneyText(textMoney, inv.money)

        //==========================================================================================
        // Sticks Variables and Button Listeners
        //==========================================================================================
        val textSticks = view.text_inv_sticks_quan
        val sticksItem = inv.getItemByName("sticks")
        updateItemText(textSticks, sticksItem)
        updateMoneyText(view.text_inv_sticks_sell_price, sticksItem.sellValue)
        updateMoneyText(view.text_inv_sticks_buy_price, sticksItem.buyValue)

        var sticksTradeQuan = 1
        val textTradeQuan = view.text_inv_sticks_trade_amount
        textTradeQuan.text = sticksTradeQuan.toString()

        // Sell Sticks Button
        view.button_inv_sticks_sell.setOnClickListener() {
            var actualTradeQuan = sticksTradeQuan
            // If attempting to sell more than we have, sell all we have instead.
            if (sticksItem.count <= actualTradeQuan)
                actualTradeQuan = sticksItem.count
            sticksItem.decreaseCount(actualTradeQuan)
            inv.increaseMoney(sticksItem.sellValue * actualTradeQuan)
            updateMoneyText(textMoney, inv.money)
            updateItemText(textSticks, sticksItem)
        }
        // Buy Sticks Button
        view.button_inv_sticks_buy.setOnClickListener() {
            var actualTradeQuan = sticksTradeQuan
            // If attempting to buy more than we can afford, buy all that we can afford instead.
            if (inv.money <= sticksItem.buyValue * actualTradeQuan)
                actualTradeQuan = inv.money / sticksItem.buyValue
            // If attempting to buy more than we can hold, buy all that we can hold instead.
            if (sticksItem.count + actualTradeQuan >= sticksItem.max)
                actualTradeQuan = sticksItem.max - sticksItem.count
            sticksItem.increaseCount(actualTradeQuan)
            inv.decreaseMoney(sticksItem.buyValue * actualTradeQuan)
            updateMoneyText(textMoney, inv.money)
            updateItemText(textSticks, sticksItem)
        }

        // Sticks Plus Button (increments the amount of sticks to buy/sell)
        view.button_inv_sticks_plus.setOnClickListener() {
            if (sticksTradeQuan < sticksItem.max) sticksTradeQuan += 1
            textTradeQuan.text = sticksTradeQuan.toString()
        }
        // Sticks Minus Button (decrements the amount of sticks to buy/sell)
        view.button_inv_sticks_minus.setOnClickListener() {
            if (sticksTradeQuan > 1) sticksTradeQuan -= 1
            textTradeQuan.text = sticksTradeQuan.toString()
        }

        return view
    }
}