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
import android.widget.ProgressBar
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

    //==============================================================================================
    // updateQuantityText: Update the TextView for an item to display its current count.
    //==============================================================================================
    private fun updateQuantityText(text: TextView, item: Item) {
        text.text = "  " + item.count.toString() + "/" + item.max.toString() + "\n " + item.name
    }

    //==============================================================================================
    // updateMoneyText: Update a TextView so it represents the format $<integer>
    //==============================================================================================
    private fun updateMoneyText(textMoney: TextView, money: Int) {
        textMoney.text = "$" + money.toString()
    }

    //==============================================================================================
    // setupInvItemListeners: This procedure sets up and references all UI elements in the
    // inventory fragment for a particular item given its item name as a string. The UI elements are
    // named in a uniform fashion so that they can be easily referenced in such fashion. This
    // essentially eliminates redundancy in the code.
    //==============================================================================================
    private fun setupInvItemListeners(v: View, itemName: String) {
        val item = inv.getItemByName(itemName)
        val pkg = "com.example.idlecraft"
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
        val view = inflater.inflate(R.layout.fragment_inventory, container, false)
        act = activity as MainActivity
        act!!.saveInv()
        inv = act!!.inventory

        val invItems = arrayOf("sticks", "rocks", "hide", "clay", "metal", "oil", "paper",
            "spear", "sword", "brick", "house", "castle", "lamp", "book")
        invItems.forEach {
            setupInvItemListeners(view, it)
        }
        return view
    }
}
    /*
    //==========================================================================================
    // Constant thread to update all text values
    //==========================================================================================
    Thread(Runnable {
        while(true) {
            updateMoneyText(textMoney, inv.money)

            updateItemText(textSticks, sticksItem)
            updateMoneyText(view.text_inv_sticks_sell_price, sticksItem.sellValue)
            updateMoneyText(view.text_inv_sticks_buy_price, sticksItem.buyValue)

            updateItemText(textRocks, rocksItem)
            updateMoneyText(view.text_inv_rocks_sell_price, rocksItem.sellValue)
            updateMoneyText(view.text_inv_rocks_buy_price, rocksItem.buyValue)

            updateItemText(textHide, hideItem)
            updateMoneyText(view.text_inv_hide_sell_price, hideItem.sellValue)
            updateMoneyText(view.text_inv_hide_buy_price, hideItem.buyValue)

            updateItemText(textClay, clayItem)
            updateMoneyText(view.text_inv_clay_sell_price, clayItem.sellValue)
            updateMoneyText(view.text_inv_clay_buy_price, clayItem.buyValue)

            updateItemText(textMetal, metalItem)
            updateMoneyText(view.text_inv_metal_sell_price, metalItem.sellValue)
            updateMoneyText(view.text_inv_metal_buy_price, metalItem.buyValue)

            updateItemText(textOil, oilItem)
            updateMoneyText(view.text_inv_oil_sell_price, oilItem.sellValue)
            updateMoneyText(view.text_inv_oil_buy_price, oilItem.buyValue)

            updateItemText(textPaper, paperItem)
            updateMoneyText(view.text_inv_paper_sell_price, paperItem.sellValue)
            updateMoneyText(view.text_inv_paper_buy_price, paperItem.buyValue)

            updateItemText(textSpear, spearItem)
            updateMoneyText(view.text_inv_spear_sell_price, spearItem.sellValue)
            updateMoneyText(view.text_inv_spear_buy_price, spearItem.buyValue)

            updateItemText(textSword, swordItem)
            updateMoneyText(view.text_inv_sword_sell_price, swordItem.sellValue)
            updateMoneyText(view.text_inv_sword_buy_price, swordItem.buyValue)

            updateItemText(textBrick, brickItem)
            updateMoneyText(view.text_inv_brick_sell_price, brickItem.sellValue)
            updateMoneyText(view.text_inv_brick_buy_price, brickItem.buyValue)

            updateItemText(textHouse, houseItem)
            updateMoneyText(view.text_inv_house_sell_price, houseItem.sellValue)
            updateMoneyText(view.text_inv_house_buy_price, houseItem.buyValue)

            updateItemText(textCastle, castleItem)
            updateMoneyText(view.text_inv_castle_sell_price, castleItem.sellValue)
            updateMoneyText(view.text_inv_castle_buy_price, castleItem.buyValue)

            updateItemText(textLamp, lampItem)
            updateMoneyText(view.text_inv_lamp_sell_price, lampItem.sellValue)
            updateMoneyText(view.text_inv_lamp_buy_price, lampItem.buyValue)

            updateItemText(textBook, bookItem)
            updateMoneyText(view.text_inv_book_sell_price, bookItem.sellValue)
            updateMoneyText(view.text_inv_book_buy_price, bookItem.buyValue)
            try { Thread.sleep(3) }
            catch (e: InterruptedException) { e.printStackTrace() }
        }
    }).start()
    */