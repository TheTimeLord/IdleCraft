package com.example.idlecraft.ui.craft

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CraftViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Craft Stuff Here! (Eventually)"
    }
    val text: LiveData<String> = _text
}