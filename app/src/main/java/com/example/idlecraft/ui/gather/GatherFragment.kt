package com.example.idlecraft.ui.gather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.idlecraft.R

class GatherFragment : Fragment() {

    private lateinit var gatherViewModel: GatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gatherViewModel =
            ViewModelProviders.of(this).get(GatherViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gather, container, false)
        val textView: TextView = root.findViewById(R.id.text_gather)
        gatherViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}