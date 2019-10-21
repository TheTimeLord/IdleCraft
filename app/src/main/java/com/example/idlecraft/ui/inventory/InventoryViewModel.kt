package com.example.idlecraft.ui.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InventoryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "See Your Stuff Here! (Eventually)"
    }
    val text: LiveData<String> = _text
}