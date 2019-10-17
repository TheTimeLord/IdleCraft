package com.example.idlecraft.ui.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShopViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Buy Stuff Here! (Eventually)"
    }
    val text: LiveData<String> = _text
}