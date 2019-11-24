package com.example.idlecraft.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.idlecraft.MainActivity
import com.example.idlecraft.R
import com.example.idlecraft.mechanics.Inventory
import com.example.idlecraft.mechanics.Item

class CraftFragment : Fragment() {
    private var act: MainActivity? = null
    private lateinit var inv: Inventory

    // updateItemText: Update the TextView for an item to display its current count.
    private fun updateItemText(text: TextView, item : Item) {
        text.text = item.count.toString() + "/" + item.max.toString() + "\n" + item.name
    }

    // updateReqText: Update all requirement TextViews for a craftable item
    private fun updateReqText(req1: TextView, req2: TextView, req3: TextView, item: Item) {
        val reqItem1 = inv.getItemByName(item.req1)
        val reqItem2 = inv.getItemByName(item.req2)
        val reqItem3 = inv.getItemByName(item.req3)

        if (item.req1 != null)
            req1.text = reqItem1.count.toString() + "/" + item.reqAmount1.toString() + " " + item.req1
        else req1.text = ""

        if (item.req2 != null)
            req2.text = reqItem2.count.toString() + "/" + item.reqAmount2.toString() + " " + item.req2
        else req2.text = ""

        if (item.req3 != null)
            req3.text = reqItem3.count.toString() + "/" + item.reqAmount3.toString()  + " " + item.req3
        else req3.text = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_craft, container, false)
        act = activity as MainActivity
        act!!.saveInv()
        inv = act!!.inventory

        // Update Thread: activate Crafting
        act!!.updateThreadCrafting = true
        act!!.updateThreadInventory = false
        var craftThread = act!!.updateThreadCrafting

        //==========================================================================================
        // Spear Variables and Button Listeners
        //==========================================================================================
        val textSpear = view.findViewById<TextView>(R.id.text_craft_spear_count)
        val progressBarSpear = view.findViewById<ProgressBar>(R.id.progress_craft_spear)
        val spearItem = inv.getItemByName("spear")

        var spearCraftCount = 1
        val textSpearAmount = view.findViewById<TextView>(R.id.text_craft_spear_amount)
        textSpearAmount.text = spearCraftCount.toString()

        val textSpearReq1 = view.findViewById<TextView>(R.id.text_craft_spear_req1)
        val textSpearReq2 = view.findViewById<TextView>(R.id.text_craft_spear_req2)
        val textSpearReq3 = view.findViewById<TextView>(R.id.text_craft_spear_req3)


        view.findViewById<ImageButton>(R.id.image_shop_sticks).setOnClickListener {
            var craftCount = inv.howManyCanCraft(spearItem)
            if (spearCraftCount < craftCount) craftCount = spearCraftCount
            if (progressBarSpear.progress != 0 || spearItem.count >= spearItem.max || !inv.isCraftable(spearItem, craftCount))
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBarSpear.max) {
                    progress += 1
                    progressBarSpear.progress = progress
                    try { Thread.sleep(16) } // 3 seconds
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBarSpear.progress = 0     // Reset progress bar
                inv.craftItem(spearItem, craftCount)
                // runOnUiThread allows the thread to update UI objects
            }).start()
        }

        // Plus Button
        view.findViewById<ImageButton>(R.id.button_craft_spear_plus).setOnClickListener {
            if (spearCraftCount < spearItem.max) spearCraftCount += 1
            textSpearAmount.text = spearCraftCount.toString()
        }
        // Minus Button
        view.findViewById<ImageButton>(R.id.button_craft_spear_minus).setOnClickListener {
            if (spearCraftCount > 1) spearCraftCount -= 1
            textSpearAmount.text = spearCraftCount.toString()
        }

        //==========================================================================================
        // Sword Variables and Button Listeners
        //==========================================================================================
        val textSword = view.findViewById<TextView>(R.id.text_craft_sword_count)
        val progressBarSword = view.findViewById<ProgressBar>(R.id.progress_craft_sword)
        val swordItem = inv.getItemByName("sword")

        var swordCraftCount = 1
        val textSwordAmount = view.findViewById<TextView>(R.id.text_craft_sword_amount)
        textSwordAmount.text = swordCraftCount.toString()

        val textSwordReq1 = view.findViewById<TextView>(R.id.text_craft_sword_req1)
        val textSwordReq2 = view.findViewById<TextView>(R.id.text_craft_sword_req2)
        val textSwordReq3 = view.findViewById<TextView>(R.id.text_craft_sword_req3)


        view.findViewById<ImageButton>(R.id.button_craft_sword).setOnClickListener {
            var craftCount = inv.howManyCanCraft(swordItem)
            if (swordCraftCount < craftCount) craftCount = swordCraftCount
            if (progressBarSword.progress != 0 || swordItem.count >= swordItem.max || !inv.isCraftable(swordItem, craftCount))
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBarSword.max) {
                    progress += 1
                    progressBarSword.progress = progress
                    try { Thread.sleep(16) } // 3 seconds
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBarSword.progress = 0     // Reset progress bar
                inv.craftItem(swordItem, craftCount)
                // runOnUiThread allows the thread to update UI objects
            }).start()
        }

        // Plus Button
        view.findViewById<ImageButton>(R.id.button_craft_sword_plus).setOnClickListener {
            if (swordCraftCount < swordItem.max) swordCraftCount += 1
            textSwordAmount.text = swordCraftCount.toString()
        }
        // Minus Button
        view.findViewById<ImageButton>(R.id.button_craft_sword_minus).setOnClickListener {
            if (swordCraftCount > 1) swordCraftCount -= 1
            textSwordAmount.text = swordCraftCount.toString()
        }

        //==========================================================================================
        // Brick Variables and Button Listeners
        //==========================================================================================
        val textBrick = view.findViewById<TextView>(R.id.text_craft_brick_count)
        val progressBarBrick = view.findViewById<ProgressBar>(R.id.progress_craft_brick)
        val brickItem = inv.getItemByName("brick")

        var brickCraftCount = 1
        val textBrickAmount = view.findViewById<TextView>(R.id.text_craft_brick_amount)
        textBrickAmount.text = brickCraftCount.toString()

        val textBrickReq1 = view.findViewById<TextView>(R.id.text_craft_brick_req1)
        val textBrickReq2 = view.findViewById<TextView>(R.id.text_craft_brick_req2)
        val textBrickReq3 = view.findViewById<TextView>(R.id.text_craft_brick_req3)


        view.findViewById<ImageButton>(R.id.button_craft_brick).setOnClickListener {
            var craftCount = inv.howManyCanCraft(brickItem)
            if (brickCraftCount < craftCount) craftCount = brickCraftCount
            if (progressBarBrick.progress != 0 || brickItem.count >= brickItem.max || !inv.isCraftable(brickItem, craftCount))
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBarBrick.max) {
                    progress += 1
                    progressBarBrick.progress = progress
                    try { Thread.sleep(16) } // 3 seconds
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBarBrick.progress = 0     // Reset progress bar
                inv.craftItem(brickItem, craftCount)
                // runOnUiThread allows the thread to update UI objects
            }).start()
        }

        // Plus Button
        view.findViewById<ImageButton>(R.id.button_craft_brick_plus).setOnClickListener {
            if (brickCraftCount < brickItem.max) brickCraftCount += 1
            textBrickAmount.text = brickCraftCount.toString()
        }
        // Minus Button
        view.findViewById<ImageButton>(R.id.button_craft_brick_minus).setOnClickListener {
            if (brickCraftCount > 1) brickCraftCount -= 1
            textBrickAmount.text = brickCraftCount.toString()
        }

        //==========================================================================================
        // House Variables and Button Listeners
        //==========================================================================================
        val textHouse = view.findViewById<TextView>(R.id.text_craft_house_count)
        val progressBarHouse = view.findViewById<ProgressBar>(R.id.progress_craft_house)
        val houseItem = inv.getItemByName("house")

        var houseCraftCount = 1
        val textHouseAmount = view.findViewById<TextView>(R.id.text_craft_house_amount)
        textHouseAmount.text = houseCraftCount.toString()

        val textHouseReq1 = view.findViewById<TextView>(R.id.text_craft_house_req1)
        val textHouseReq2 = view.findViewById<TextView>(R.id.text_craft_house_req2)
        val textHouseReq3 = view.findViewById<TextView>(R.id.text_craft_house_req3)


        view.findViewById<ImageButton>(R.id.button_craft_house).setOnClickListener {
            var craftCount = inv.howManyCanCraft(houseItem)
            if (houseCraftCount < craftCount) craftCount = houseCraftCount
            if (progressBarHouse.progress != 0 || houseItem.count >= houseItem.max || !inv.isCraftable(houseItem, craftCount))
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBarHouse.max) {
                    progress += 1
                    progressBarHouse.progress = progress
                    try { Thread.sleep(16) } // 3 seconds
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBarHouse.progress = 0     // Reset progress bar
                inv.craftItem(houseItem, craftCount)
                // runOnUiThread allows the thread to update UI objects
            }).start()
        }

        // Plus Button
        view.findViewById<ImageButton>(R.id.button_craft_house_plus).setOnClickListener {
            if (houseCraftCount < houseItem.max) houseCraftCount += 1
            textHouseAmount.text = houseCraftCount.toString()
        }
        // Minus Button
        view.findViewById<ImageButton>(R.id.button_craft_house_minus).setOnClickListener {
            if (houseCraftCount > 1) houseCraftCount -= 1
            textHouseAmount.text = houseCraftCount.toString()
        }

        //==========================================================================================
        // Castle Variables and Button Listeners
        //==========================================================================================
        val textCastle = view.findViewById<TextView>(R.id.text_craft_castle_count)
        val progressBarCastle = view.findViewById<ProgressBar>(R.id.progress_craft_castle)
        val castleItem = inv.getItemByName("castle")

        var castleCraftCount = 1
        val textCastleAmount = view.findViewById<TextView>(R.id.text_craft_castle_amount)
        textCastleAmount.text = castleCraftCount.toString()

        val textCastleReq1 = view.findViewById<TextView>(R.id.text_craft_castle_req1)
        val textCastleReq2 = view.findViewById<TextView>(R.id.text_craft_castle_req2)
        val textCastleReq3 = view.findViewById<TextView>(R.id.text_craft_castle_req3)


        view.findViewById<ImageButton>(R.id.button_craft_castle).setOnClickListener {
            var craftCount = inv.howManyCanCraft(castleItem)
            if (castleCraftCount < craftCount) craftCount = castleCraftCount
            if (progressBarCastle.progress != 0 || castleItem.count >= castleItem.max || !inv.isCraftable(castleItem, craftCount))
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBarCastle.max) {
                    progress += 1
                    progressBarCastle.progress = progress
                    try { Thread.sleep(16) } // 3 seconds
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBarCastle.progress = 0     // Reset progress bar
                inv.craftItem(castleItem, craftCount)
                // runOnUiThread allows the thread to update UI objects

            }).start()
        }

        // Plus Button
        view.findViewById<ImageButton>(R.id.button_craft_castle_plus).setOnClickListener {
            if (castleCraftCount < castleItem.max) castleCraftCount += 1
            textCastleAmount.text = castleCraftCount.toString()
        }
        // Minus Button
        view.findViewById<ImageButton>(R.id.button_craft_castle_minus).setOnClickListener {
            if (castleCraftCount > 1) castleCraftCount -= 1
            textCastleAmount.text = castleCraftCount.toString()
        }

        //==========================================================================================
        // Lamp Variables and Button Listeners
        //==========================================================================================
        val textLamp = view.findViewById<TextView>(R.id.text_craft_lamp_count)
        val progressBarLamp = view.findViewById<ProgressBar>(R.id.progress_craft_lamp)
        val lampItem = inv.getItemByName("lamp")

        var lampCraftCount = 1
        val textLampAmount = view.findViewById<TextView>(R.id.text_craft_lamp_amount)
        textLampAmount.text = lampCraftCount.toString()

        val textLampReq1 = view.findViewById<TextView>(R.id.text_craft_lamp_req1)
        val textLampReq2 = view.findViewById<TextView>(R.id.text_craft_lamp_req2)
        val textLampReq3 = view.findViewById<TextView>(R.id.text_craft_lamp_req3)


        view.findViewById<ImageButton>(R.id.button_craft_lamp).setOnClickListener {
            var craftCount = inv.howManyCanCraft(lampItem)
            if (lampCraftCount < craftCount) craftCount = lampCraftCount
            if (progressBarLamp.progress != 0 || lampItem.count >= lampItem.max || !inv.isCraftable(lampItem, craftCount))
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBarLamp.max) {
                    progress += 1
                    progressBarLamp.progress = progress
                    try { Thread.sleep(16) } // 3 seconds
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBarLamp.progress = 0     // Reset progress bar
                inv.craftItem(lampItem, craftCount)
                // runOnUiThread allows the thread to update UI objects
            }).start()
        }

        // Plus Button
        view.findViewById<ImageButton>(R.id.button_craft_lamp_plus).setOnClickListener {
            if (lampCraftCount < lampItem.max) lampCraftCount += 1
            textLampAmount.text = lampCraftCount.toString()
        }
        // Minus Button
        view.findViewById<ImageButton>(R.id.button_craft_lamp_minus).setOnClickListener {
            if (lampCraftCount > 1) lampCraftCount -= 1
            textLampAmount.text = lampCraftCount.toString()
        }

        //==========================================================================================
        // Book Variables and Button Listeners
        //==========================================================================================
        val textBook = view.findViewById<TextView>(R.id.text_craft_book_count)
        val progressBarBook = view.findViewById<ProgressBar>(R.id.progress_craft_book)
        val bookItem = inv.getItemByName("book")

        var bookCraftCount = 1
        val textBookAmount = view.findViewById<TextView>(R.id.text_craft_book_amount)
        textBookAmount.text = bookCraftCount.toString()

        val textBookReq1 = view.findViewById<TextView>(R.id.text_craft_book_req1)
        val textBookReq2 = view.findViewById<TextView>(R.id.text_craft_book_req2)
        val textBookReq3 = view.findViewById<TextView>(R.id.text_craft_book_req3)


        view.findViewById<ImageButton>(R.id.button_craft_book).setOnClickListener {
            var craftCount = inv.howManyCanCraft(bookItem)
            if (bookCraftCount < craftCount) craftCount = bookCraftCount
            if (progressBarBook.progress != 0 || bookItem.count >= bookItem.max || !inv.isCraftable(bookItem, craftCount))
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBarBook.max) {
                    progress += 1
                    progressBarBook.progress = progress
                    try { Thread.sleep(16) } // 3 seconds
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBarBook.progress = 0     // Reset progress bar
                inv.craftItem(bookItem, craftCount)
                // runOnUiThread allows the thread to update UI objects
            }).start()
        }

        // Plus Button
        view.findViewById<ImageButton>(R.id.button_craft_book_plus).setOnClickListener {
            if (bookCraftCount < bookItem.max) bookCraftCount += 1
            textBookAmount.text = bookCraftCount.toString()
        }
        // Minus Button
        view.findViewById<ImageButton>(R.id.button_craft_book_minus).setOnClickListener {
            if (bookCraftCount > 1) bookCraftCount -= 1
            textBookAmount.text = bookCraftCount.toString()
        }

        //==========================================================================================
        // Constant thread to update all text values
        //==========================================================================================
        Thread(Runnable {

            while (craftThread) {
                Thread.sleep(25)
                // constantly check to see if thread needs to stay alive
                craftThread = act!!.updateThreadCrafting

                activity?.runOnUiThread {
                    updateItemText(textSpear, spearItem)
                    updateReqText(textSpearReq1, textSpearReq2, textSpearReq3, spearItem)

                    updateItemText(textSword, swordItem)
                    updateReqText(textSwordReq1, textSwordReq2, textSwordReq3, swordItem)

                    updateItemText(textBrick, brickItem)
                    updateReqText(textBrickReq1, textBrickReq2, textBrickReq3, brickItem)

                    updateItemText(textHouse, houseItem)
                    updateReqText(textHouseReq1, textHouseReq2, textHouseReq3, houseItem)

                    updateItemText(textCastle, castleItem)
                    updateReqText(textCastleReq1, textCastleReq2, textCastleReq3, castleItem)

                    updateItemText(textLamp, lampItem)
                    updateReqText(textLampReq1, textLampReq2, textLampReq3, lampItem)

                    updateReqText(textBookReq1, textBookReq2, textBookReq3, bookItem)
                    updateItemText(textBook, bookItem)
                }
            }
        }).start()

        return view
    }
}