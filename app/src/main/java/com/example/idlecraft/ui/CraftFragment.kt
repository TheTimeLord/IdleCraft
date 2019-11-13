package com.example.idlecraft.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.idlecraft.MainActivity
import com.example.idlecraft.R
import com.example.idlecraft.mechanics.Inventory
import com.example.idlecraft.mechanics.Item
import kotlinx.android.synthetic.main.fragment_craft.view.*

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

        //==========================================================================================
        // Spear Variables and Button Listeners
        //==========================================================================================
        val textSpear = view.text_craft_spear_count
        val progressBarSpear = view.progress_craft_spear
        val spearItem = inv.getItemByName("spear")
        //updateItemText(textSpear, spearItem)

        var spearCraftCount = 1
        val textSpearAmount = view.text_craft_spear_amount
        textSpearAmount.text = spearCraftCount.toString()

        val textSpearReq1 = view.text_craft_spear_req1
        val textSpearReq2 = view.text_craft_spear_req2
        val textSpearReq3 = view.text_craft_spear_req3
        updateReqText(textSpearReq1, textSpearReq2, textSpearReq3, spearItem)


        view.button_craft_spear.setOnClickListener {
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
                /*
                activity?.runOnUiThread {
                    updateItemText(textSpear, spearItem)
                    updateReqText(textSpearReq1, textSpearReq2, textSpearReq3, spearItem)
                } */
            }).start()
        }

        // Plus Button
        view.button_craft_spear_plus.setOnClickListener {
            if (spearCraftCount < spearItem.max) spearCraftCount += 1
            textSpearAmount.text = spearCraftCount.toString()
        }
        // Minus Button
        view.button_craft_spear_minus.setOnClickListener {
            if (spearCraftCount > 1) spearCraftCount -= 1
            textSpearAmount.text = spearCraftCount.toString()
        }

        //==========================================================================================
        // Thread to update text views
        //==========================================================================================

        //TODO: Fix this thread so that it does not cause crashes or program hang
        Thread(Runnable {
            while(true) {
                updateItemText(textSpear, spearItem)
                //updateReqText(textSpearReq1, textSpearReq2, textSpearReq3, spearItem)
            }
        }).start()

        return view
    }
}