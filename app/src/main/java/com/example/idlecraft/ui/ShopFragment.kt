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
        text.text = "         Max Inventory x" + item.max.toString()
    }
    // updateRateText: Update the TextView for an item to display its gathering rate.
    private fun updateRateText(text: TextView, item: Item) {
        text.text = "         Gathering Rate x" + item.rate.toString()
    }
    // updatePriceTexts: Update the TextViews for the prices of upgrading inventory and rate. The prices
    // are multiplied by the current trade amount.
    private fun updatePriceTexts(textInv: TextView, textRate: TextView, item: Item, tradeAmount: Int) {
        textInv.text = "$" + (item.buyMax * tradeAmount).toString()
        textRate.text = "$" + (item.buyRate * tradeAmount).toString()
    }
    // updateIncreaseAmountTexts: Update the TextViews for how much inv and rate increase. The prices
    // are multiplied by the current trade amount.
    private fun updateIncreaseAmountTexts(textInv: TextView, textRate: TextView, item: Item, tradeAmount: Int) {
        textInv.text = "+" + (item.incInv * tradeAmount).toString()
        textRate.text = "+" + (item.incRate * tradeAmount).toString()
    }
    // updateMoneyText: Update the TextView for money to newVal
    private fun updateMoneyText(text: TextView, newVal: Int) {
        text.text = "$" + newVal.toString()
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
        val textSticksMax = view.text_shop_sticks_inv
        val textSticksRate = view.text_shop_sticks_rate
        val textSticksInvPrice = view.text_shop_sticks_inv_price
        val textSticksRatePrice = view.text_shop_sticks_rate_price
        val sticksItem = inv.getItemByName("sticks")
        val textSticksTradeAmount = view.text_shop_sticks_trade_amount
        val textSticksInvInc = view.text_shop_sticks_inv_increase
        val textSticksRateInc = view.text_shop_sticks_rate_increase

        var sticksTradeAmount = 1
        textSticksTradeAmount.text = sticksTradeAmount.toString()
        updateInvText(textSticksMax, sticksItem)
        updateRateText(textSticksRate, sticksItem)
        updatePriceTexts(textSticksInvPrice, textSticksRatePrice, sticksItem, sticksTradeAmount)
        updateIncreaseAmountTexts(textSticksInvInc, textSticksRateInc, sticksItem, sticksTradeAmount)

        // Buy Max Inventory Button
        view.button_shop_sticks_buy_inv.setOnClickListener {
            // If attempting to buy more than we have money for, buy all we can instead.
            var actualTradeAmount = sticksTradeAmount
            if (sticksItem.buyMax * sticksTradeAmount >= inv.money)
                actualTradeAmount = inv.money / sticksItem.buyMax

            sticksItem.increaseMax(sticksItem.incInv * actualTradeAmount)
            updateInvText(textSticksMax, sticksItem)
            inv.money -= sticksItem.buyMax * actualTradeAmount
            updateMoneyText(textMoney, inv.money)
        }
        // Buy Rate Button
        view.button_shop_sticks_buy_rate.setOnClickListener {
            // If attempting to buy more than we have money for, buy all we can instead.
            var actualTradeAmount = sticksTradeAmount
            if (sticksItem.buyRate * sticksTradeAmount >= inv.money)
                actualTradeAmount = inv.money / sticksItem.buyRate

            sticksItem.rate += sticksItem.incRate * actualTradeAmount
            updateRateText(textSticksRate, sticksItem)
            inv.money -= sticksItem.buyRate * actualTradeAmount
            updateMoneyText(textMoney, inv.money)
        }

        // Plus Button (increase trade amount)
        view.button_shop_sticks_plus.setOnClickListener {
            sticksTradeAmount += 1
            textSticksTradeAmount.text = sticksTradeAmount.toString()
            updatePriceTexts(textSticksInvPrice, textSticksRatePrice, sticksItem, sticksTradeAmount)
            updateIncreaseAmountTexts(textSticksInvInc, textSticksRateInc, sticksItem, sticksTradeAmount)
        }
        // Minus Button (decrease trade amount)
        view.button_shop_sticks_minus.setOnClickListener {
            if (sticksTradeAmount > 1) sticksTradeAmount -= 1
            textSticksTradeAmount.text = sticksTradeAmount.toString()
            updatePriceTexts(textSticksInvPrice, textSticksRatePrice, sticksItem, sticksTradeAmount)
            updateIncreaseAmountTexts(textSticksInvInc, textSticksRateInc, sticksItem, sticksTradeAmount)
        }

        return view
    }
}