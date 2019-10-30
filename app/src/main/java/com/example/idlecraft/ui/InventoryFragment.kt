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

    // updateTextNum: Update the TextView to display the number newVal
    private fun updateTextNum(text: TextView, newVal: Int) {
        text.text = newVal.toString()
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
        val textRocks = view.text_inv_rocks_cnt
        val textSellRocks = view.text_inv_rocks_sellCnt
        val sticksItem = inv.getItemByName("sticks")
        val rocksItem = inv.getItemByName("rocks")
        val rocksPrice = 3
        val sticksPrice = 3

        var rocksSellCnt = 1


        // UI Initialization
        updateMoneyText(textMoney, inv.money)
        updateItemText(textSticks, sticksItem)
        updateItemText(textRocks, rocksItem)
        updateTextNum(textSellRocks, rocksSellCnt)

        // Insert fragment code here

        // Sell Sticks Button
        view.button_sell_sticks.setOnClickListener() {
            if(sticksItem.count > 0) {
                sticksItem.decreaseCount(1)
                inv.increaseMoney(sticksPrice)
                updateMoneyText(textMoney, inv.money)
                updateItemText(textSticks, sticksItem)
            }
        }

        //Buy/Sell Rocks Buttons
        view.button_inv_rocks_buy.setOnClickListener() {
            if(rocksItem.count < rocksItem.max )
                if(inv.money >= (rocksPrice * 2 * rocksSellCnt)) {
                    inv.decreaseMoney(rocksPrice * 2 * rocksSellCnt)
                    rocksItem.increaseCount(rocksSellCnt)
                    updateMoneyText(textMoney, inv.money)
                    updateItemText(textRocks, rocksItem)
                }
        }
        view.button_inv_rocks_sell.setOnClickListener() {
            if(rocksItem.count >= rocksSellCnt) {
                rocksItem.decreaseCount(rocksSellCnt)
                inv.increaseMoney(rocksPrice * rocksSellCnt)
                updateMoneyText(textMoney, inv.money)
                updateItemText(textRocks, rocksItem)
            }
        }

        //Incr/Decr Sell amount Rocks
        view.button_inv_rocks_incrSell.setOnClickListener() {
            if(rocksSellCnt < rocksItem.max) {
                rocksSellCnt += 1
                updateTextNum(textSellRocks, rocksSellCnt)
            }
        }
        view.button_inv_rocks_decrSell.setOnClickListener() {
            if(rocksSellCnt > 1) {
                rocksSellCnt -= 1
                updateTextNum(textSellRocks, rocksSellCnt)
            }
        }

        return view
    }
}