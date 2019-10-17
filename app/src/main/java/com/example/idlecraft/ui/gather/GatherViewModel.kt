package com.example.idlecraft.ui.gather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GatherViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Gather Resources Here! (Eventually)"
    }
    val text: LiveData<String> = _text
}