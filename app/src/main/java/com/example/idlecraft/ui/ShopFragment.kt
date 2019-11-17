package com.example.idlecraft.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.idlecraft.MainActivity
import com.example.idlecraft.R
import com.example.idlecraft.mechanics.Item
import kotlinx.android.synthetic.main.fragment_shop.view.*

class ShopFragment : Fragment() {
    private var act: MainActivity? = null

    // updateInvText: Update the TextView for an item to display its max inventory count.
    private fun updateInvText(text: TextView, item : Item) {
        text.text = "   Max Inventory    " + item.max.toString()
    }
    // updateRateText: Update the TextView for an item to display its gathering rate.
    private fun updateRateText(text: TextView, item : Item) {
        text.text = "   Gathering Rate  +" + item.rate.toString()
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
        val view = inflater.inflate(R.layout.fragment_shop, container, false)
        act = activity as MainActivity
        act!!.saveInv()

        act = activity as MainActivity
        val inv = act!!.inventory
        val textMoney = view.text_shop_money
        updateMoneyText(textMoney, inv.money)

        //==========================================================================================
        // Sticks Shop Variables and Button Listeners
        //==========================================================================================

        // Sticks Item and UI setup
        val textSticksMax = view.text_store_sticks_max
        val textSticksRate = view.text_store_sticks_speed
        val textIncreaseAmount = view.text_store_sticks_count
        val sticksItem = inv.getItemByName("sticks")
        updateInvText(textSticksMax, sticksItem)
        updateRateText(textSticksRate, sticksItem)
        view.text_store_sticks_cost_max.text = "$" + sticksItem.buyMax.toString()
        view.text_store_sticks_cost_speed.text = "$" + sticksItem.buyRate.toString()


        //==========================================================================================
        // UI Event Listeners
        //==========================================================================================

        // Buy Max Sticks Button
        view.text_shop_sticks_inv_label.setOnClickListener {
            if ((sticksItem.buyMax * textIncreaseAmmount.text.toString().toInt()) <= inv.money) {
                sticksItem.increaseMax(textIncreaseAmmount.text.toString().toInt())
                updateItemText(textSticks, sticksItem)
                inv.money -= (sticksItem.buyMax * textIncreaseAmmount.text.toString().toInt())
            }
        }
        // Buy Sticks Rate Increase Button
        view.button_store_sticks_rate_buy.setOnClickListener {
            if ((sticksItem.buyRate * textIncreaseAmmount.text.toString().toInt()) <= inv.money) {
                sticksItem.rate += textIncreaseAmmount.text.toString().toInt()
                updateItemRateText(textSticksRate, sticksItem)
                inv.money -= (sticksItem.buyRate * textIncreaseAmmount.text.toString().toInt())
            }
        }
        // Increase Sticks Amount
        view.button_store_sticks_count_inc.setOnClickListener {
            if (textIncreaseAmmount.text.toString().toInt() + 1 > 0) {
                textIncreaseAmmount.text =
                    (textIncreaseAmmount.text.toString().toInt() + 1).toString()
                view.text_store_sticks_cost_max.text = "$" + (sticksItem.buyMax * textIncreaseAmmount.text.toString().toInt()).toString()
                view.text_store_sticks_cost_speed.text = "$" + (sticksItem.buyRate * textIncreaseAmmount.text.toString().toInt()).toString()
            }
        }
        // Decrease Sticks Amount
        view.button_store_sticks_count_dec.setOnClickListener {
            if (textIncreaseAmmount.text.toString().toInt() - 1 > 0) {
                textIncreaseAmmount.text =
                    (textIncreaseAmmount.text.toString().toInt() - 1).toString()
                view.text_store_sticks_cost_max.text = "$" + (sticksItem.buyMax * textIncreaseAmmount.text.toString().toInt()).toString()
                view.text_store_sticks_cost_speed.text = "$" + (sticksItem.buyRate * textIncreaseAmmount.text.toString().toInt()).toString()
            }
        }

        return view
    }
}