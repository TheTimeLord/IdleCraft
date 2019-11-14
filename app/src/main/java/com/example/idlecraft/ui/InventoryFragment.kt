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

        var sticksTradeQuan = 1
        val sticksTextTradeQuan = view.text_inv_sticks_trade_amount
        sticksTextTradeQuan.text = sticksTradeQuan.toString()

        // Sell Sticks Button
        view.button_inv_sticks_sell.setOnClickListener() {
            var actualTradeQuan = sticksTradeQuan
            // If attempting to sell more than we have, sell all we have instead.
            if (sticksItem.count <= actualTradeQuan)
                actualTradeQuan = sticksItem.count
            sticksItem.decreaseCount(actualTradeQuan)
            inv.increaseMoney(sticksItem.sellValue * actualTradeQuan)
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
        }

        // Sticks Plus Button (increments the amount of sticks to buy/sell)
        view.button_inv_sticks_plus.setOnClickListener() {
            if (sticksTradeQuan < sticksItem.max) sticksTradeQuan += 1
            sticksTextTradeQuan.text = sticksTradeQuan.toString()
        }
        // Sticks Minus Button (decrements the amount of sticks to buy/sell)
        view.button_inv_sticks_minus.setOnClickListener() {
            if (sticksTradeQuan > 1) sticksTradeQuan -= 1
            sticksTextTradeQuan.text = sticksTradeQuan.toString()
        }

        //==========================================================================================
        // Rocks Variables and Button Listeners
        //==========================================================================================
        val textRocks = view.text_inv_rocks_quan
        val rocksItem = inv.getItemByName("rocks")

        var rocksTradeQuan = 1
        val rocksTextTradeQuan = view.text_inv_rocks_trade_amount
        rocksTextTradeQuan.text = rocksTradeQuan.toString()

        // Sell Rocks Button
        view.button_inv_rocks_sell.setOnClickListener() {
            var actualTradeQuan = rocksTradeQuan
            // If attempting to sell more than we have, sell all we have instead.
            if (rocksItem.count <= actualTradeQuan)
                actualTradeQuan = rocksItem.count
            rocksItem.decreaseCount(actualTradeQuan)
            inv.increaseMoney(rocksItem.sellValue * actualTradeQuan)
        }
        // Buy Rocks Button
        view.button_inv_rocks_buy.setOnClickListener() {
            var actualTradeQuan = rocksTradeQuan
            // If attempting to buy more than we can afford, buy all that we can afford instead.
            if (inv.money <= rocksItem.buyValue * actualTradeQuan)
                actualTradeQuan = inv.money / rocksItem.buyValue
            // If attempting to buy more than we can hold, buy all that we can hold instead.
            if (rocksItem.count + actualTradeQuan >= rocksItem.max)
                actualTradeQuan = rocksItem.max - rocksItem.count
            rocksItem.increaseCount(actualTradeQuan)
            inv.decreaseMoney(rocksItem.buyValue * actualTradeQuan)
        }

        // Rocks Plus Button (increments the amount of rocks to buy/sell)
        view.button_inv_rocks_plus.setOnClickListener() {
            if (rocksTradeQuan < rocksItem.max) rocksTradeQuan += 1
            rocksTextTradeQuan.text = rocksTradeQuan.toString()
        }
        // Rocks Minus Button (decrements the amount of rocks to buy/sell)
        view.button_inv_rocks_minus.setOnClickListener() {
            if (rocksTradeQuan > 1) rocksTradeQuan -= 1
            rocksTextTradeQuan.text = rocksTradeQuan.toString()
        }

        //==========================================================================================
        // Hide Variables and Button Listeners
        //==========================================================================================
        val textHide = view.text_inv_hide_quan
        val hideItem = inv.getItemByName("hide")

        var hideTradeQuan = 1
        val hideTextTradeQuan = view.text_inv_hide_trade_amount
        hideTextTradeQuan.text = hideTradeQuan.toString()

        // Sell Hide Button
        view.button_inv_hide_sell.setOnClickListener() {
            var actualTradeQuan = hideTradeQuan
            // If attempting to sell more than we have, sell all we have instead.
            if (hideItem.count <= actualTradeQuan)
                actualTradeQuan = hideItem.count
            hideItem.decreaseCount(actualTradeQuan)
            inv.increaseMoney(hideItem.sellValue * actualTradeQuan)
        }
        // Buy Hide Button
        view.button_inv_hide_buy.setOnClickListener() {
            var actualTradeQuan = hideTradeQuan
            // If attempting to buy more than we can afford, buy all that we can afford instead.
            if (inv.money <= hideItem.buyValue * actualTradeQuan)
                actualTradeQuan = inv.money / hideItem.buyValue
            // If attempting to buy more than we can hold, buy all that we can hold instead.
            if (hideItem.count + actualTradeQuan >= hideItem.max)
                actualTradeQuan = hideItem.max - hideItem.count
            hideItem.increaseCount(actualTradeQuan)
            inv.decreaseMoney(hideItem.buyValue * actualTradeQuan)
        }

        // Hide Plus Button (increments the amount of hide to buy/sell)
        view.button_inv_hide_plus.setOnClickListener() {
            if (hideTradeQuan < hideItem.max) hideTradeQuan += 1
            hideTextTradeQuan.text = hideTradeQuan.toString()
        }
        // Hide Minus Button (decrements the amount of hide to buy/sell)
        view.button_inv_hide_minus.setOnClickListener() {
            if (hideTradeQuan > 1) hideTradeQuan -= 1
            hideTextTradeQuan.text = hideTradeQuan.toString()
        }

        //==========================================================================================
        // Clay Variables and Button Listeners
        //==========================================================================================
        val textClay = view.text_inv_clay_quan
        val clayItem = inv.getItemByName("clay")

        var clayTradeQuan = 1
        val clayTextTradeQuan = view.text_inv_clay_trade_amount
        clayTextTradeQuan.text = clayTradeQuan.toString()

        // Sell Clay Button
        view.button_inv_clay_sell.setOnClickListener() {
            var actualTradeQuan = clayTradeQuan
            // If attempting to sell more than we have, sell all we have instead.
            if (clayItem.count <= actualTradeQuan)
                actualTradeQuan = clayItem.count
            clayItem.decreaseCount(actualTradeQuan)
            inv.increaseMoney(clayItem.sellValue * actualTradeQuan)
        }
        // Buy Clay Button
        view.button_inv_clay_buy.setOnClickListener() {
            var actualTradeQuan = clayTradeQuan
            // If attempting to buy more than we can afford, buy all that we can afford instead.
            if (inv.money <= clayItem.buyValue * actualTradeQuan)
                actualTradeQuan = inv.money / clayItem.buyValue
            // If attempting to buy more than we can hold, buy all that we can hold instead.
            if (clayItem.count + actualTradeQuan >= clayItem.max)
                actualTradeQuan = clayItem.max - clayItem.count
            clayItem.increaseCount(actualTradeQuan)
            inv.decreaseMoney(clayItem.buyValue * actualTradeQuan)
        }

        // Clay Plus Button (increments the amount of clay to buy/sell)
        view.button_inv_clay_plus.setOnClickListener() {
            if (clayTradeQuan < clayItem.max) clayTradeQuan += 1
            clayTextTradeQuan.text = clayTradeQuan.toString()
        }
        // Clay Minus Button (decrements the amount of clay to buy/sell)
        view.button_inv_clay_minus.setOnClickListener() {
            if (clayTradeQuan > 1) clayTradeQuan -= 1
            clayTextTradeQuan.text = clayTradeQuan.toString()
        }

        //==========================================================================================
        // Metal Variables and Button Listeners
        //==========================================================================================
        val textMetal = view.text_inv_metal_quan
        val metalItem = inv.getItemByName("metal")

        var metalTradeQuan = 1
        val metalTextTradeQuan = view.text_inv_metal_trade_amount
        metalTextTradeQuan.text = metalTradeQuan.toString()

        // Sell Metal Button
        view.button_inv_metal_sell.setOnClickListener() {
            var actualTradeQuan = metalTradeQuan
            // If attempting to sell more than we have, sell all we have instead.
            if (metalItem.count <= actualTradeQuan)
                actualTradeQuan = metalItem.count
            metalItem.decreaseCount(actualTradeQuan)
            inv.increaseMoney(metalItem.sellValue * actualTradeQuan)
        }
        // Buy Metal Button
        view.button_inv_metal_buy.setOnClickListener() {
            var actualTradeQuan = metalTradeQuan
            // If attempting to buy more than we can afford, buy all that we can afford instead.
            if (inv.money <= metalItem.buyValue * actualTradeQuan)
                actualTradeQuan = inv.money / metalItem.buyValue
            // If attempting to buy more than we can hold, buy all that we can hold instead.
            if (metalItem.count + actualTradeQuan >= metalItem.max)
                actualTradeQuan = metalItem.max - metalItem.count
            metalItem.increaseCount(actualTradeQuan)
            inv.decreaseMoney(metalItem.buyValue * actualTradeQuan)
        }

        // Metal Plus Button (increments the amount of metal to buy/sell)
        view.button_inv_metal_plus.setOnClickListener() {
            if (metalTradeQuan < metalItem.max) metalTradeQuan += 1
            metalTextTradeQuan.text = metalTradeQuan.toString()
        }
        // Metal Minus Button (decrements the amount of metal to buy/sell)
        view.button_inv_metal_minus.setOnClickListener() {
            if (metalTradeQuan > 1) metalTradeQuan -= 1
            metalTextTradeQuan.text = metalTradeQuan.toString()
        }

        //==========================================================================================
        // Oil Variables and Button Listeners
        //==========================================================================================
        val textOil = view.text_inv_oil_quan
        val oilItem = inv.getItemByName("oil")

        var oilTradeQuan = 1
        val oilTextTradeQuan = view.text_inv_oil_trade_amount
        oilTextTradeQuan.text = oilTradeQuan.toString()

        // Sell Oil Button
        view.button_inv_oil_sell.setOnClickListener() {
            var actualTradeQuan = oilTradeQuan
            // If attempting to sell more than we have, sell all we have instead.
            if (oilItem.count <= actualTradeQuan)
                actualTradeQuan = oilItem.count
            oilItem.decreaseCount(actualTradeQuan)
            inv.increaseMoney(oilItem.sellValue * actualTradeQuan)
        }
        // Buy Oil Button
        view.button_inv_oil_buy.setOnClickListener() {
            var actualTradeQuan = oilTradeQuan
            // If attempting to buy more than we can afford, buy all that we can afford instead.
            if (inv.money <= oilItem.buyValue * actualTradeQuan)
                actualTradeQuan = inv.money / oilItem.buyValue
            // If attempting to buy more than we can hold, buy all that we can hold instead.
            if (oilItem.count + actualTradeQuan >= oilItem.max)
                actualTradeQuan = oilItem.max - oilItem.count
            oilItem.increaseCount(actualTradeQuan)
            inv.decreaseMoney(oilItem.buyValue * actualTradeQuan)
        }

        // Oil Plus Button (increments the amount of oil to buy/sell)
        view.button_inv_oil_plus.setOnClickListener() {
            if (oilTradeQuan < oilItem.max) oilTradeQuan += 1
            oilTextTradeQuan.text = oilTradeQuan.toString()
        }
        // Oil Minus Button (decrements the amount of oil to buy/sell)
        view.button_inv_oil_minus.setOnClickListener() {
            if (oilTradeQuan > 1) oilTradeQuan -= 1
            oilTextTradeQuan.text = oilTradeQuan.toString()
        }

        //==========================================================================================
        // Paper Variables and Button Listeners
        //==========================================================================================
        val textPaper = view.text_inv_paper_quan
        val paperItem = inv.getItemByName("paper")

        var paperTradeQuan = 1
        val paperTextTradeQuan = view.text_inv_paper_trade_amount
        paperTextTradeQuan.text = paperTradeQuan.toString()

        // Sell Paper Button
        view.button_inv_paper_sell.setOnClickListener() {
            var actualTradeQuan = paperTradeQuan
            // If attempting to sell more than we have, sell all we have instead.
            if (paperItem.count <= actualTradeQuan)
                actualTradeQuan = paperItem.count
            paperItem.decreaseCount(actualTradeQuan)
            inv.increaseMoney(paperItem.sellValue * actualTradeQuan)
        }
        // Buy Paper Button
        view.button_inv_paper_buy.setOnClickListener() {
            var actualTradeQuan = paperTradeQuan
            // If attempting to buy more than we can afford, buy all that we can afford instead.
            if (inv.money <= paperItem.buyValue * actualTradeQuan)
                actualTradeQuan = inv.money / paperItem.buyValue
            // If attempting to buy more than we can hold, buy all that we can hold instead.
            if (paperItem.count + actualTradeQuan >= paperItem.max)
                actualTradeQuan = paperItem.max - paperItem.count
            paperItem.increaseCount(actualTradeQuan)
            inv.decreaseMoney(paperItem.buyValue * actualTradeQuan)
        }

        // Paper Plus Button (increments the amount of paper to buy/sell)
        view.button_inv_paper_plus.setOnClickListener() {
            if (paperTradeQuan < paperItem.max) paperTradeQuan += 1
            paperTextTradeQuan.text = paperTradeQuan.toString()
        }
        // Paper Minus Button (decrements the amount of paper to buy/sell)
        view.button_inv_paper_minus.setOnClickListener() {
            if (paperTradeQuan > 1) paperTradeQuan -= 1
            paperTextTradeQuan.text = paperTradeQuan.toString()
        }

        //==========================================================================================
        // Spear Variables and Button Listeners
        //==========================================================================================
        val textSpear = view.text_inv_spear_quan
        val spearItem = inv.getItemByName("spear")

        var spearTradeQuan = 1
        val spearTextTradeQuan = view.text_inv_spear_trade_amount
        spearTextTradeQuan.text = spearTradeQuan.toString()

        // Sell Spear Button
        view.button_inv_spear_sell.setOnClickListener() {
            var actualTradeQuan = spearTradeQuan
            // If attempting to sell more than we have, sell all we have instead.
            if (spearItem.count <= actualTradeQuan)
                actualTradeQuan = spearItem.count
            spearItem.decreaseCount(actualTradeQuan)
            inv.increaseMoney(spearItem.sellValue * actualTradeQuan)
        }
        // Buy Spear Button
        view.button_inv_spear_buy.setOnClickListener() {
            var actualTradeQuan = spearTradeQuan
            // If attempting to buy more than we can afford, buy all that we can afford instead.
            if (inv.money <= spearItem.buyValue * actualTradeQuan)
                actualTradeQuan = inv.money / spearItem.buyValue
            // If attempting to buy more than we can hold, buy all that we can hold instead.
            if (spearItem.count + actualTradeQuan >= spearItem.max)
                actualTradeQuan = spearItem.max - spearItem.count
            spearItem.increaseCount(actualTradeQuan)
            inv.decreaseMoney(spearItem.buyValue * actualTradeQuan)
        }

        // Spear Plus Button (increments the amount of spear to buy/sell)
        view.button_inv_spear_plus.setOnClickListener() {
            if (spearTradeQuan < spearItem.max) spearTradeQuan += 1
            spearTextTradeQuan.text = spearTradeQuan.toString()
        }
        // Spear Minus Button (decrements the amount of spear to buy/sell)
        view.button_inv_spear_minus.setOnClickListener() {
            if (spearTradeQuan > 1) spearTradeQuan -= 1
            spearTextTradeQuan.text = spearTradeQuan.toString()
        }

        //==========================================================================================
        // Sword Variables and Button Listeners
        //==========================================================================================
        val textSword = view.text_inv_sword_quan
        val swordItem = inv.getItemByName("sword")

        var swordTradeQuan = 1
        val swordTextTradeQuan = view.text_inv_sword_trade_amount
        swordTextTradeQuan.text = swordTradeQuan.toString()

        // Sell Sword Button
        view.button_inv_sword_sell.setOnClickListener() {
            var actualTradeQuan = swordTradeQuan
            // If attempting to sell more than we have, sell all we have instead.
            if (swordItem.count <= actualTradeQuan)
                actualTradeQuan = swordItem.count
            swordItem.decreaseCount(actualTradeQuan)
            inv.increaseMoney(swordItem.sellValue * actualTradeQuan)
        }
        // Buy Sword Button
        view.button_inv_sword_buy.setOnClickListener() {
            var actualTradeQuan = swordTradeQuan
            // If attempting to buy more than we can afford, buy all that we can afford instead.
            if (inv.money <= swordItem.buyValue * actualTradeQuan)
                actualTradeQuan = inv.money / swordItem.buyValue
            // If attempting to buy more than we can hold, buy all that we can hold instead.
            if (swordItem.count + actualTradeQuan >= swordItem.max)
                actualTradeQuan = swordItem.max - swordItem.count
            swordItem.increaseCount(actualTradeQuan)
            inv.decreaseMoney(swordItem.buyValue * actualTradeQuan)
        }

        // Sword Plus Button (increments the amount of sword to buy/sell)
        view.button_inv_sword_plus.setOnClickListener() {
            if (swordTradeQuan < swordItem.max) swordTradeQuan += 1
            swordTextTradeQuan.text = swordTradeQuan.toString()
        }
        // Sword Minus Button (decrements the amount of sword to buy/sell)
        view.button_inv_sword_minus.setOnClickListener() {
            if (swordTradeQuan > 1) swordTradeQuan -= 1
            swordTextTradeQuan.text = swordTradeQuan.toString()
        }

        //==========================================================================================
        // Brick Variables and Button Listeners
        //==========================================================================================
        val textBrick = view.text_inv_brick_quan
        val brickItem = inv.getItemByName("brick")

        var brickTradeQuan = 1
        val brickTextTradeQuan = view.text_inv_brick_trade_amount
        brickTextTradeQuan.text = brickTradeQuan.toString()

        // Sell Brick Button
        view.button_inv_brick_sell.setOnClickListener() {
            var actualTradeQuan = brickTradeQuan
            // If attempting to sell more than we have, sell all we have instead.
            if (brickItem.count <= actualTradeQuan)
                actualTradeQuan = brickItem.count
            brickItem.decreaseCount(actualTradeQuan)
            inv.increaseMoney(brickItem.sellValue * actualTradeQuan)
        }
        // Buy Brick Button
        view.button_inv_brick_buy.setOnClickListener() {
            var actualTradeQuan = brickTradeQuan
            // If attempting to buy more than we can afford, buy all that we can afford instead.
            if (inv.money <= brickItem.buyValue * actualTradeQuan)
                actualTradeQuan = inv.money / brickItem.buyValue
            // If attempting to buy more than we can hold, buy all that we can hold instead.
            if (brickItem.count + actualTradeQuan >= brickItem.max)
                actualTradeQuan = brickItem.max - brickItem.count
            brickItem.increaseCount(actualTradeQuan)
            inv.decreaseMoney(brickItem.buyValue * actualTradeQuan)
        }

        // Brick Plus Button (increments the amount of brick to buy/sell)
        view.button_inv_brick_plus.setOnClickListener() {
            if (brickTradeQuan < brickItem.max) brickTradeQuan += 1
            brickTextTradeQuan.text = brickTradeQuan.toString()
        }
        // Brick Minus Button (decrements the amount of brick to buy/sell)
        view.button_inv_brick_minus.setOnClickListener() {
            if (brickTradeQuan > 1) brickTradeQuan -= 1
            brickTextTradeQuan.text = brickTradeQuan.toString()
        }

        //==========================================================================================
        // House Variables and Button Listeners
        //==========================================================================================
        val textHouse = view.text_inv_house_quan
        val houseItem = inv.getItemByName("house")

        var houseTradeQuan = 1
        val houseTextTradeQuan = view.text_inv_house_trade_amount
        houseTextTradeQuan.text = houseTradeQuan.toString()

        // Sell House Button
        view.button_inv_house_sell.setOnClickListener() {
            var actualTradeQuan = houseTradeQuan
            // If attempting to sell more than we have, sell all we have instead.
            if (houseItem.count <= actualTradeQuan)
                actualTradeQuan = houseItem.count
            houseItem.decreaseCount(actualTradeQuan)
            inv.increaseMoney(houseItem.sellValue * actualTradeQuan)
        }
        // Buy House Button
        view.button_inv_house_buy.setOnClickListener() {
            var actualTradeQuan = houseTradeQuan
            // If attempting to buy more than we can afford, buy all that we can afford instead.
            if (inv.money <= houseItem.buyValue * actualTradeQuan)
                actualTradeQuan = inv.money / houseItem.buyValue
            // If attempting to buy more than we can hold, buy all that we can hold instead.
            if (houseItem.count + actualTradeQuan >= houseItem.max)
                actualTradeQuan = houseItem.max - houseItem.count
            houseItem.increaseCount(actualTradeQuan)
            inv.decreaseMoney(houseItem.buyValue * actualTradeQuan)
        }

        // House Plus Button (increments the amount of house to buy/sell)
        view.button_inv_house_plus.setOnClickListener() {
            if (houseTradeQuan < houseItem.max) houseTradeQuan += 1
            houseTextTradeQuan.text = houseTradeQuan.toString()
        }
        // House Minus Button (decrements the amount of house to buy/sell)
        view.button_inv_house_minus.setOnClickListener() {
            if (houseTradeQuan > 1) houseTradeQuan -= 1
            houseTextTradeQuan.text = houseTradeQuan.toString()
        }

        //==========================================================================================
        // Castle Variables and Button Listeners
        //==========================================================================================
        val textCastle = view.text_inv_castle_quan
        val castleItem = inv.getItemByName("castle")

        var castleTradeQuan = 1
        val castleTextTradeQuan = view.text_inv_castle_trade_amount
        castleTextTradeQuan.text = castleTradeQuan.toString()

        // Sell Castle Button
        view.button_inv_castle_sell.setOnClickListener() {
            var actualTradeQuan = castleTradeQuan
            // If attempting to sell more than we have, sell all we have instead.
            if (castleItem.count <= actualTradeQuan)
                actualTradeQuan = castleItem.count
            castleItem.decreaseCount(actualTradeQuan)
            inv.increaseMoney(castleItem.sellValue * actualTradeQuan)
        }
        // Buy Castle Button
        view.button_inv_castle_buy.setOnClickListener() {
            var actualTradeQuan = castleTradeQuan
            // If attempting to buy more than we can afford, buy all that we can afford instead.
            if (inv.money <= castleItem.buyValue * actualTradeQuan)
                actualTradeQuan = inv.money / castleItem.buyValue
            // If attempting to buy more than we can hold, buy all that we can hold instead.
            if (castleItem.count + actualTradeQuan >= castleItem.max)
                actualTradeQuan = castleItem.max - castleItem.count
            castleItem.increaseCount(actualTradeQuan)
            inv.decreaseMoney(castleItem.buyValue * actualTradeQuan)
        }

        // Castle Plus Button (increments the amount of castle to buy/sell)
        view.button_inv_castle_plus.setOnClickListener() {
            if (castleTradeQuan < castleItem.max) castleTradeQuan += 1
            castleTextTradeQuan.text = castleTradeQuan.toString()
        }
        // Castle Minus Button (decrements the amount of castle to buy/sell)
        view.button_inv_castle_minus.setOnClickListener() {
            if (castleTradeQuan > 1) castleTradeQuan -= 1
            castleTextTradeQuan.text = castleTradeQuan.toString()
        }

        //==========================================================================================
        // Lamp Variables and Button Listeners
        //==========================================================================================
        val textLamp = view.text_inv_lamp_quan
        val lampItem = inv.getItemByName("lamp")

        var lampTradeQuan = 1
        val lampTextTradeQuan = view.text_inv_lamp_trade_amount
        lampTextTradeQuan.text = lampTradeQuan.toString()

        // Sell Lamp Button
        view.button_inv_lamp_sell.setOnClickListener() {
            var actualTradeQuan = lampTradeQuan
            // If attempting to sell more than we have, sell all we have instead.
            if (lampItem.count <= actualTradeQuan)
                actualTradeQuan = lampItem.count
            lampItem.decreaseCount(actualTradeQuan)
            inv.increaseMoney(lampItem.sellValue * actualTradeQuan)
        }
        // Buy Lamp Button
        view.button_inv_lamp_buy.setOnClickListener() {
            var actualTradeQuan = lampTradeQuan
            // If attempting to buy more than we can afford, buy all that we can afford instead.
            if (inv.money <= lampItem.buyValue * actualTradeQuan)
                actualTradeQuan = inv.money / lampItem.buyValue
            // If attempting to buy more than we can hold, buy all that we can hold instead.
            if (lampItem.count + actualTradeQuan >= lampItem.max)
                actualTradeQuan = lampItem.max - lampItem.count
            lampItem.increaseCount(actualTradeQuan)
            inv.decreaseMoney(lampItem.buyValue * actualTradeQuan)
        }

        // Lamp Plus Button (increments the amount of lamp to buy/sell)
        view.button_inv_lamp_plus.setOnClickListener() {
            if (lampTradeQuan < lampItem.max) lampTradeQuan += 1
            lampTextTradeQuan.text = lampTradeQuan.toString()
        }
        // Lamp Minus Button (decrements the amount of lamp to buy/sell)
        view.button_inv_lamp_minus.setOnClickListener() {
            if (lampTradeQuan > 1) lampTradeQuan -= 1
            lampTextTradeQuan.text = lampTradeQuan.toString()
        }

        //==========================================================================================
        // Book Variables and Button Listeners
        //==========================================================================================
        val textBook = view.text_inv_book_quan
        val bookItem = inv.getItemByName("book")

        var bookTradeQuan = 1
        val bookTextTradeQuan = view.text_inv_book_trade_amount
        bookTextTradeQuan.text = bookTradeQuan.toString()

        // Sell Book Button
        view.button_inv_book_sell.setOnClickListener() {
            var actualTradeQuan = bookTradeQuan
            // If attempting to sell more than we have, sell all we have instead.
            if (bookItem.count <= actualTradeQuan)
                actualTradeQuan = bookItem.count
            bookItem.decreaseCount(actualTradeQuan)
            inv.increaseMoney(bookItem.sellValue * actualTradeQuan)
        }
        // Buy Book Button
        view.button_inv_book_buy.setOnClickListener() {
            var actualTradeQuan = bookTradeQuan
            // If attempting to buy more than we can afford, buy all that we can afford instead.
            if (inv.money <= bookItem.buyValue * actualTradeQuan)
                actualTradeQuan = inv.money / bookItem.buyValue
            // If attempting to buy more than we can hold, buy all that we can hold instead.
            if (bookItem.count + actualTradeQuan >= bookItem.max)
                actualTradeQuan = bookItem.max - bookItem.count
            bookItem.increaseCount(actualTradeQuan)
            inv.decreaseMoney(bookItem.buyValue * actualTradeQuan)
        }

        // Book Plus Button (increments the amount of book to buy/sell)
        view.button_inv_book_plus.setOnClickListener() {
            if (bookTradeQuan < bookItem.max) bookTradeQuan += 1
            bookTextTradeQuan.text = bookTradeQuan.toString()
        }
        // Book Minus Button (decrements the amount of book to buy/sell)
        view.button_inv_book_minus.setOnClickListener() {
            if (bookTradeQuan > 1) bookTradeQuan -= 1
            bookTextTradeQuan.text = bookTradeQuan.toString()
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
            }
        }).start()

        return view
    }
}