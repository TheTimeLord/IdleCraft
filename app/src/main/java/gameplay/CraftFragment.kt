//==================================================================================================
// CraftFragment.kt
//
// Authors: Brian Andrus, Eduardo Zamora, Nathan Lakritz, Saar Sayfan, Travis Kerns
//
// Description: This file contains the implementation of a fragment that contains functionality
// for in-game crafting. The fragment is created upon opening and destroyed upon closing. A
// number of button listeners are setup that interact with the underlying data structure that
// contains all player data: Inventory.
//==================================================================================================
package gameplay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.idlecraft.R
import mechanics.Inventory
import mechanics.Item

class CraftFragment : Fragment() {
    // Private Member Fields
    private var act: MainActivity? = null
    private lateinit var inv: Inventory
    private val pkg = "com.example.idlecraft"

    //==============================================================================================
    // updateItemText: Update the TextView for an item to display its current count.
    //==============================================================================================
    private fun updateItemText(text: TextView, item : Item) {
        val newText = "${item.count}/${item.max}\n${item.name}"
        text.text = newText
    }

    //==============================================================================================
    // updateReqText: Update all requirement TextViews for a craftable item
    //==============================================================================================
    private fun updateReqText(req1: TextView, req2: TextView, req3: TextView, item: Item) {
        val reqItem1 = inv.getItemByName(item.req1)
        val reqItem2 = inv.getItemByName(item.req2)
        val reqItem3 = inv.getItemByName(item.req3)

        if (item.req1 != null) req1.text = "${reqItem1.count}/${item.reqAmount1} ${item.req1}"
        else req1.text = ""

        if (item.req2 != null) req2.text = "${reqItem2.count}/${item.reqAmount2} ${item.req2}"
        else req2.text = ""

        if (item.req3 != null) req3.text = "${reqItem3.count}/${item.reqAmount3} ${item.req3}"
        else req3.text = ""
    }

    //==============================================================================================
    // setupCraftItemListeners: This procedure sets up and references all UI elements in the
    // crafting fragment for a particular item given its item name as a string. The UI elements are
    // named in a uniform fashion so that they can be easily referenced in such fashion. This
    // essentially eliminates redundancy in the code.
    //==============================================================================================
    private fun setupCraftItemListeners(v: View, itemName: String) {
        val item = inv.getItemByName(itemName)
        var amount = 1

        // Declare strings to reference a set of UI elements for an item
        val countStr       = "text_craft_${itemName}_count"
        val progressBarStr = "progress_craft_${itemName}"
        val craftAmountStr = "text_craft_${itemName}_amount"
        val craftReqStr    = "text_craft_${itemName}_req"
        val craftButtonStr = "button_craft_${itemName}"

        // Use the UI strings to reference each UI element
        val itemCount         = v.findViewById<TextView>(resources.getIdentifier(countStr, "id", pkg))
        val progressBar       = v.findViewById<ProgressBar>(resources.getIdentifier(progressBarStr, "id", pkg))
        val craftAmount       = v.findViewById<TextView>(resources.getIdentifier(craftAmountStr, "id", pkg))
        val craftReq1         = v.findViewById<TextView>(resources.getIdentifier(craftReqStr + "1", "id", pkg))
        val craftReq2         = v.findViewById<TextView>(resources.getIdentifier(craftReqStr + "2", "id", pkg))
        val craftReq3         = v.findViewById<TextView>(resources.getIdentifier(craftReqStr + "3", "id", pkg))
        val craftButton       = v.findViewById<ImageButton>(resources.getIdentifier(craftButtonStr, "id", pkg))
        val amountPlusButton  = v.findViewById<ImageButton>(resources.getIdentifier(craftButtonStr + "_plus", "id", pkg))
        val amountMinusButton = v.findViewById<ImageButton>(resources.getIdentifier(craftButtonStr + "_minus", "id", pkg))

        // Update the UI with current values upon entering the fragment
        craftAmount.text = amount.toString()
        updateItemText(itemCount, item)
        updateReqText(craftReq1, craftReq2, craftReq3, item)

        // Called when a crafting button is clicked. It ensures that the player has the required
        // crafting materials required. If the player does not have enough materials to craft
        // the desired amount, the "max" items that they can craft will be crafted instead. This
        // button also uses a thread to animate a progress bar.
        craftButton.setOnClickListener {
            var amountToCraft = inv.howManyCanCraft(item)
            if (amount < amountToCraft) amountToCraft = amount
            if (progressBar.progress != 0 || item.count >= item.max || !inv.isCraftable(item, amountToCraft))
                return@setOnClickListener

            act!!.startProgress(itemName, "craft", amountToCraft)
        }

        // The plus button allows a player to craft more items at a time.
        amountPlusButton.setOnClickListener {
            if (amount < item.max) amount += 1
            craftAmount.text = amount.toString()
        }
        // The minus button allows a player to craft less items at a time.
        amountMinusButton.setOnClickListener {
            if (amount > 1) amount -= 1
            craftAmount.text = amount.toString()
        }
    }

    //==============================================================================================
    // onCreateView: Called upon fragment creation. It sets up a reference to the global inventory
    // object, then sets up button listeners by calling setupCraftItemListeners for each crafting
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
        val view = inflater.inflate(R.layout.fragment_craft, container, false)
        act!!.saveInv()
        inv = act!!.inventory

        // Setup UI elements and button listeners
        val craftItems = arrayOf("spear", "sword", "brick", "house", "castle", "lamp", "book")
        craftItems.forEach {
            setupCraftItemListeners(view, it)
        }

        act!!.currentFragment = "craft"

        // Thread constantly updates all crafting TextViews to reflect the player's inventory
        // while this fragment is open. This is needed because inventory values are constantly
        // changing and updating.
        Thread(Runnable {
            while (act!!.currentFragment == "craft") {
                craftItems.forEach {
                    val item = inv.getItemByName(it)

                    // Declare strings to reference a set of UI elements for an item
                    val countStr    = "text_craft_${it}_count"
                    val craftReqStr = "text_craft_${it}_req"

                    // Use the UI strings to reference each UI element then update them
                    val itemCount = view.findViewById<TextView>(resources.getIdentifier(countStr, "id", pkg))
                    val craftReq1 = view.findViewById<TextView>(resources.getIdentifier(craftReqStr + "1", "id", pkg))
                    val craftReq2 = view.findViewById<TextView>(resources.getIdentifier(craftReqStr + "2", "id", pkg))
                    val craftReq3 = view.findViewById<TextView>(resources.getIdentifier(craftReqStr + "3", "id", pkg))

                    // Update the quantity and rate TextViews
                    act!!.runOnUiThread {
                        updateItemText(itemCount, item)
                        updateReqText(craftReq1, craftReq2, craftReq3, item)
                    }
                }
                // Sleep and constantly check to see if thread needs to stay alive
                Thread.sleep(25)
            }
        }).start()
        return view
    }
}