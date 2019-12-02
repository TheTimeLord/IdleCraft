//==================================================================================================
// ShopFragment.kt
//
// Authors: Brian Andrus, Eduardo Zamora, Nathan Lakritz, Saar Sayfan, Travis Kerns
//
// Description: This file contains the implementation of a fragment that contains functionality
// for in-game shop. The fragment is created upon opening and destroyed upon closing. A
// number of button listeners are setup that interact with the underlying data structure that
// contains all player data: Inventory.
//==================================================================================================
package com.example.idlecraft.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.idlecraft.MainActivity
import com.example.idlecraft.R
import com.example.idlecraft.mechanics.Inventory
import com.example.idlecraft.mechanics.Item
import kotlinx.android.synthetic.main.fragment_shop.view.*

class ShopFragment : Fragment() {
    // Private Member Fields
    private var act: MainActivity? = null
    private lateinit var inv: Inventory
    private val pkg = "com.example.idlecraft"

    //==============================================================================================
    // TextView Updating Procedures
    //==============================================================================================
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

    //==============================================================================================
    // setupShopItemListeners: This procedure sets up and references all UI elements in the
    // shop fragment for a particular item given its item name as a string. The UI elements are
    // named in a uniform fashion so that they can be easily referenced in such fashion. This
    // essentially eliminates redundancy in the code.
    //==============================================================================================
    private fun setupShopItemListeners(v: View, itemName: String) {
        val item = inv.getItemByName(itemName)
        val money = v.text_shop_money
        var tradeAmount = 1

        // Declare strings to reference a set of UI elements for an item
        val invAmountStr     = "text_shop_${itemName}_inv"
        val rateAmountStr    = "text_shop_${itemName}_rate"
        val invPriceStr      = "text_shop_${itemName}_inv_price"
        val ratePriceStr     = "text_shop_${itemName}_rate_price"
        val invIncStr        = "text_shop_${itemName}_inv_increase"
        val rateIncStr       = "text_shop_${itemName}_rate_increase"
        val tradeQuanStr     = "text_shop_${itemName}_trade_amount"
        val buyInvButtonStr  = "button_shop_${itemName}_buy_inv"
        val buyRateButtonStr = "button_shop_${itemName}_buy_rate"
        val plusButtonStr    = "button_shop_${itemName}_plus"
        val minusButtonStr   = "button_shop_${itemName}_minus"

        // Use the UI strings to reference each UI element
        val invAmount     = v.findViewById<TextView>(resources.getIdentifier(invAmountStr, "id", pkg))
        val rateAmount    = v.findViewById<TextView>(resources.getIdentifier(rateAmountStr, "id", pkg))
        val invPrice      = v.findViewById<TextView>(resources.getIdentifier(invPriceStr, "id", pkg))
        val ratePrice     = v.findViewById<TextView>(resources.getIdentifier(ratePriceStr, "id", pkg))
        val invInc        = v.findViewById<TextView>(resources.getIdentifier(invIncStr, "id", pkg))
        val rateInc       = v.findViewById<TextView>(resources.getIdentifier(rateIncStr, "id", pkg))
        val tradeQuan     = v.findViewById<TextView>(resources.getIdentifier(tradeQuanStr, "id", pkg))
        val buyInvButton  = v.findViewById<ImageButton>(resources.getIdentifier(buyInvButtonStr, "id", pkg))
        val buyRateButton = v.findViewById<ImageButton>(resources.getIdentifier(buyRateButtonStr, "id", pkg))
        val plusButton    = v.findViewById<ImageButton>(resources.getIdentifier(plusButtonStr, "id", pkg))
        val minusButton   = v.findViewById<ImageButton>(resources.getIdentifier(minusButtonStr, "id", pkg))

        // Update the UI with current values upon entering the fragment
        updateInvText(invAmount, item)
        updateRateText(rateAmount, item)
        updatePriceTexts(invPrice, ratePrice, item, tradeAmount)
        updateIncreaseAmountTexts(invInc, rateInc, item, tradeAmount)
        tradeQuan.text = tradeAmount.toString()
        updateMoneyText(money, inv.money)

        // Buy Inventory Button: Increases the player's max inventory space for an item.
        buyInvButton.setOnClickListener {
            // If attempting to buy more than we have money for, buy all we can instead.
            var actualTradeAmount = tradeAmount
            if (item.buyMax * tradeAmount >= inv.money)
                actualTradeAmount = inv.money / item.buyMax

            item.increaseMax(item.incInv * actualTradeAmount)
            updateInvText(invAmount, item)
            inv.money -= item.buyMax * actualTradeAmount
            updateMoneyText(money, inv.money)
        }

        // Buy Rate Button: Increases the amount gathered of an item.
        buyRateButton.setOnClickListener {
            // If attempting to buy more than we have money for, buy all we can instead.
            var actualTradeAmount = tradeAmount
            if (item.buyRate * tradeAmount >= inv.money)
                actualTradeAmount = inv.money / item.buyRate

            item.rate += item.incRate * actualTradeAmount
            updateRateText(rateAmount, item)
            inv.money -= item.buyRate * actualTradeAmount
            updateMoneyText(money, inv.money)
        }

        // Plus Button: increments the trade quantity.
        plusButton.setOnClickListener {
            tradeAmount += 1
            tradeQuan.text = tradeAmount.toString()
            updatePriceTexts(invPrice, ratePrice, item, tradeAmount)
            updateIncreaseAmountTexts(invInc, rateInc, item, tradeAmount)
        }
        // Minus Button: decrements the trade quantity.
        minusButton.setOnClickListener {
            if (tradeAmount > 1) tradeAmount -= 1
            tradeQuan.text = tradeAmount.toString()
            updatePriceTexts(invPrice, ratePrice, item, tradeAmount)
            updateIncreaseAmountTexts(invInc, rateInc, item, tradeAmount)
        }
    }

    //==============================================================================================
    // onCreateView: Called upon fragment creation. It sets up a reference to the global inventory
    // object, then sets up button listeners by calling setupShopItemListeners for each shop item.
    //==============================================================================================
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        act = activity as MainActivity
        // set currentFragment to empty string before view is inflated
        act!!.currentFragment = ""
        val view = inflater.inflate(R.layout.fragment_shop, container, false)
        act!!.saveInv()
        inv = act!!.inventory
        val textMoney = view.text_shop_money
        updateMoneyText(textMoney, inv.money)

        // Setup UI elements and button listeners
        val shopItems = arrayOf("sticks", "rocks", "hide", "clay", "metal", "oil", "paper")
        shopItems.forEach {
            setupShopItemListeners(view, it)
        }

        act!!.currentFragment = "shop"

        return view
    }
}