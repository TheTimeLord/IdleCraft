package com.example.idlecraft.ui

import android.media.Image
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

    private fun setupCraftItemListeners(v:View, itemName: String) {
        val item = inv.getItemByName(itemName)
        val pkg = "com.example.idlecraft"
        var amount = 1

        val countString = "text_craft_${itemName}_count"
        val progressBarString = "progress_craft_${itemName}"
        val craftAmountString = "text_craft_${itemName}_amount"
        val craftReqString = "text_craft_${itemName}_req"
        val craftButtonString = "button_craft_${itemName}"

        val itemCount = v!!.findViewById<TextView>(resources.getIdentifier(countString, "id", pkg))
        val progressBar = v!!.findViewById<ProgressBar>(resources.getIdentifier(progressBarString, "id", pkg))
        val craftAmount = v!!.findViewById<TextView>(resources.getIdentifier(craftAmountString, "id", pkg))
        val craftReq1 = v!!.findViewById<TextView>(resources.getIdentifier(craftReqString + "1", "id", pkg))
        val craftReq2 = v!!.findViewById<TextView>(resources.getIdentifier(craftReqString + "2", "id", pkg))
        val craftReq3 = v!!.findViewById<TextView>(resources.getIdentifier(craftReqString + "3", "id", pkg))
        val craftButton = v!!.findViewById<ImageButton>(resources.getIdentifier(craftButtonString, "id", pkg))
        val amountPlusButton = v!!.findViewById<ImageButton>(resources.getIdentifier(craftButtonString + "_plus", "id", pkg))
        val amountMinusButton = v!!.findViewById<ImageButton>(resources.getIdentifier(craftButtonString + "_minus", "id", pkg))

        craftAmount.text = amount.toString()
        updateItemText(itemCount, item)
        updateReqText(craftReq1, craftReq2, craftReq3, item)

        craftButton.setOnClickListener {
            var amountToCraft = inv.howManyCanCraft(item)
            if (amount < amountToCraft) amountToCraft = amount
            if (progressBar.progress != 0 || item.count >= item.max || !inv.isCraftable(item, amountToCraft))
                return@setOnClickListener
            Thread(Runnable {
                var progress = 0
                while (progress < progressBar.max) {
                    progress += 1
                    progressBar.progress = progress
                    try { Thread.sleep(16) } // 3 seconds
                    catch (e: InterruptedException) { e.printStackTrace() }
                }
                progressBar.progress = 0     // Reset progress bar
                inv.craftItem(item, amountToCraft)
                // runOnUiThread allows the thread to update UI objects
                activity?.runOnUiThread {
                    updateItemText(itemCount, item)
                    updateReqText(craftReq1, craftReq2, craftReq3, item)
                }
            }).start()
        }

        // Plus Button
        amountPlusButton.setOnClickListener {
            if (amount < item.max) amount += 1
            craftAmount.text = amount.toString()
        }
        // Minus Button
        amountMinusButton.setOnClickListener {
            if (amount > 1) amount -= 1
            craftAmount.text = amount.toString()
        }

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

        setupCraftItemListeners(view, "spear")
        setupCraftItemListeners(view, "sword")
        setupCraftItemListeners(view, "brick")
        setupCraftItemListeners(view, "house")
        setupCraftItemListeners(view, "castle")
        setupCraftItemListeners(view, "lamp")
        setupCraftItemListeners(view, "book")

        return view
    }
}