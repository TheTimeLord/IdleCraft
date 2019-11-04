package com.example.idlecraft.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.idlecraft.MainActivity
import com.example.idlecraft.R
import kotlinx.android.synthetic.main.fragment_craft.view.*

class CraftFragment : Fragment() {
    private var act: MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_craft, container, false)
        act = activity as MainActivity
        val inv = act!!.inventory
        val textSpear = view.text_craft_spear
        val textSpearCount = view.text_craft_spear_count
        val progressBar = view.progress_craft_spear
        val spearItem = inv.getItemByName("spear")

        var count = 1

        textSpear.text = spearItem.count.toString()
        textSpearCount.text = count.toString()

        view.button_craft_spear.setOnClickListener {
            if (progressBar.progress != 0 || spearItem.count >= spearItem.max || !inv.isCraftable("spear", count))
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
                inv.craftItem("spear", count)
                textSpear.text = spearItem.count.toString()
            }).start()
        }

        view.button_craft_spear_count_inc.setOnClickListener {
            count += 1
            textSpearCount.text = count.toString()
        }

        view.button_craft_spear_count_dec.setOnClickListener {
            if (count > 0) {
                count -= 1
            }
            textSpearCount.text = count.toString()
        }

        view.button_hide_craft_spear.setOnClickListener {
            view.group_craft_spear.visibility = View.GONE
        }

        return view
    }
}