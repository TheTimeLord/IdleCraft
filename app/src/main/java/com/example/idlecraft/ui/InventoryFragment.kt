//==================================================================================================
// InventoryFragment.kt
//
// Authors: Brian Andrus, Eduardo Zamora, Nathan Lakritz, Saar Sayfan, Travis Kerns
//
// Description: This file contains the implementation of a fragment that contains functionality
// for in-game inventory. The fragment is created upon opening and destroyed upon closing. A
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
import com.example.idlecraft.R
import com.example.idlecraft.MainActivity
import com.example.idlecraft.mechanics.Inventory
import com.example.idlecraft.mechanics.Item
import kotlinx.android.synthetic.main.fragment_inventory.view.*

class InventoryFragment : Fragment() {
    // Private Member Fields
    private var act: MainActivity? = null
    private lateinit var inv: Inventory
    private val pkg = "com.example.idlecraft"

    //==============================================================================================
    // updateQuantityText: Update the TextView for an item to display its current count.
    //==============================================================================================
    private fun updateQuantityText(text: TextView, item: Item) {
        val newText = "  ${item.count}/${item.max}\n${item.name}"
        text.text = newText
    }

    //==============================================================================================
    // updateMoneyText: Update a TextView so it represents the format $<integer>
    //==============================================================================================
    private fun updateMoneyText(textMoney: TextView, money: Int) {
        val newText = "$${money}"
        textMoney.text = newText
    }

    //==============================================================================================
    // setupInvItemListeners: This procedure sets up and references all UI elements in the
    // inventory fragment for a particular item given its item name as a string. The UI elements are
    // named in a uniform fashion so that they can be easily referenced in such fashion. This
    // essentially eliminates redundancy in the code.
    //==============================================================================================
    private fun setupInvItemListeners(v: View, itemName: String) {
        val item = inv.getItemByName(itemName)
        val money = v.text_inv_money
        var tradeQuan = 1


        // Declare strings to reference a set of UI elements for an item
        val quantityStr     = "text_inv_${itemName}_quan"
        val buyPriceStr     = "text_inv_${itemName}_buy_price"
        val sellPriceStr    = "text_inv_${itemName}_sell_price"
        val tradeAmountStr  = "text_inv_${itemName}_trade_amount"
        val buyButtonStr    = "button_inv_${itemName}_buy"
        val sellButtonStr   = "button_inv_${itemName}_sell"
        val plusButtonStr   = "button_inv_${itemName}_plus"
        val minusButtonStr  = "button_inv_${itemName}_minus"

        // Use the UI strings to reference each UI element
        val itemQuantity    = v.findViewById<TextView>(resources.getIdentifier(quantityStr, "id", pkg))
        val itemBuyPrice    = v.findViewById<TextView>(resources.getIdentifier(buyPriceStr, "id", pkg))
        val itemSellPrice   = v.findViewById<TextView>(resources.getIdentifier(sellPriceStr, "id", pkg))
        val itemTradeAmount = v.findViewById<TextView>(resources.getIdentifier(tradeAmountStr, "id", pkg))
        val buyButton       = v.findViewById<ImageButton>(resources.getIdentifier(buyButtonStr, "id", pkg))
        val sellButton      = v.findViewById<ImageButton>(resources.getIdentifier(sellButtonStr, "id", pkg))
        val plusButton      = v.findViewById<ImageButton>(resources.getIdentifier(plusButtonStr, "id", pkg))
        val minusButton     = v.findViewById<ImageButton>(resources.getIdentifier(minusButtonStr, "id", pkg))

        // Update the UI with current values upon entering the fragment
        updateQuantityText(itemQuantity, item)
        updateMoneyText(itemSellPrice, item.sellValue)
        updateMoneyText(itemBuyPrice, item.buyValue)
        updateMoneyText(money, inv.money)
        itemTradeAmount.text = tradeQuan.toString()

        // Buy Button: It increases an item by the amount desired for purchase, and decreases
        // the player's money accordingly.
        buyButton.setOnClickListener() {
            // If attempting to buy more than we can afford, buy all that we can afford instead.
            var actualTradeQuan = tradeQuan
            if (inv.money <= item.buyValue * actualTradeQuan)
                actualTradeQuan = inv.money / item.buyValue

            // If attempting to buy more than we can hold, buy all that we can hold instead.
            if (item.count + actualTradeQuan >= item.max)
                actualTradeQuan = item.max - item.count

            item.increaseCount(actualTradeQuan)
            inv.decreaseMoney(item.buyValue * actualTradeQuan)
            updateMoneyText(money, inv.money)
            updateQuantityText(itemQuantity, item)
        }

        // Sell Button: It decreases an item by the amount desired for purchase, and increases
        // the player's money accordingly.
        sellButton.setOnClickListener() {
            // If attempting to sell more than we have, sell all we have instead.
            var actualTradeQuan = tradeQuan
            if (item.count <= actualTradeQuan)
                actualTradeQuan = item.count

            item.decreaseCount(actualTradeQuan)
            inv.increaseMoney(item.sellValue * actualTradeQuan)
            updateMoneyText(money, inv.money)
            updateQuantityText(itemQuantity, item)
        }

        // Plus Button: increments the trade quantity.
        plusButton.setOnClickListener() {
            if (tradeQuan < item.max) tradeQuan += 1
            itemTradeAmount.text = tradeQuan.toString()
        }
        // Minus Button: decrements the trade quantity.
        minusButton.setOnClickListener() {
            if (tradeQuan > 1) tradeQuan -= 1
            itemTradeAmount.text = tradeQuan.toString()
        }
    }

    //==============================================================================================
    // onCreateView: Called upon fragment creation. It sets up a reference to the global inventory
    // object, then sets up button listeners by calling setupInvItemListeners for each inventory
    // item.
    //==============================================================================================
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        act = activity as MainActivity
        // set currentFragment to empty string before view is inflated
        act!!.currentFragment = ""
        val view = inflater.inflate(R.layout.fragment_inventory, container, false)
        act!!.saveInv()
        inv = act!!.inventory
        val textMoney = view.text_inv_money
        updateMoneyText(textMoney, inv.money)

        // Setup UI elements and button listeners
        val invItems = arrayOf("sticks", "rocks", "hide", "clay", "metal", "oil", "paper",
            "spear", "sword", "brick", "house", "castle", "lamp", "book")
        invItems.forEach {
            setupInvItemListeners(view, it)
        }

        act!!.currentFragment = "inventory"

        // Thread constantly updates all inventory TextViews to reflect the player's inventory
        // while this fragment is open. This is needed because inventory values are constantly
        // changing and updating.
        Thread(Runnable {
            while(act!!.currentFragment == "inventory") {
                invItems.forEach {
                    val item = inv.getItemByName(it)
                    updateMoneyText(textMoney, inv.money)

                    // Declare strings to reference a set of UI elements for an item
                    val quantityStr     = "text_inv_${it}_quan"
                    val buyPriceStr     = "text_inv_${it}_buy_price"
                    val sellPriceStr    = "text_inv_${it}_sell_price"

                    // Use the UI strings to reference each UI element then update them
                    val itemQuantity    = view.findViewById<TextView>(resources.getIdentifier(quantityStr, "id", pkg))
                    val itemBuyPrice    = view.findViewById<TextView>(resources.getIdentifier(buyPriceStr, "id", pkg))
                    val itemSellPrice   = view.findViewById<TextView>(resources.getIdentifier(sellPriceStr, "id", pkg))
                    updateQuantityText(itemQuantity, item)
                    updateMoneyText(itemSellPrice, item.sellValue)
                    updateMoneyText(itemBuyPrice, item.buyValue)
                    updateMoneyText(textMoney, inv.money)
                }
                // Sleep and constantly check to see if thread needs to stay alive
                Thread.sleep(25)
            }
        }).start()
        return view
    }
}