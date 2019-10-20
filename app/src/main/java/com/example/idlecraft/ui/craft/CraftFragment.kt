package com.example.idlecraft.ui.craft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.idlecraft.R

class CraftFragment : Fragment() {

    private lateinit var craftViewModel: CraftViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        craftViewModel = ViewModelProviders.of(this).get(CraftViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_craft, container, false)
        val textView: TextView = root.findViewById(R.id.text_craft)
        craftViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}