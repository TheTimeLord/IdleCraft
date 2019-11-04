package com.example.idlecraft.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
        var count = 1

        view.text_craft_spear_count.text = inv.getItemByName("spear").count.toString()
        view.text_craft_spear_craft_count.text = count.toString()

        view.button_craft_spear.setOnClickListener {
            // have to convert to string first and then int because the text field is complex
            inv.craftItem(inv.getItemByName("spear"), count)
        }

        view.button_craft_spear_count_inc.setOnClickListener {
            count += 1
            view.text_craft_spear_craft_count.text = count.toString()
        }

        view.button_craft_spear_count_dec.setOnClickListener {
            if (count > 0) {
                count -= 1
            }
            view.text_craft_spear_craft_count.text = count.toString()
        }

        view.button_hide_craft_spear.setOnClickListener {
            view.group_craft_spear.visibility = View.GONE
        }

        return view
    }
}