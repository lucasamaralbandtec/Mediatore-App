package com.example.mediatore.ui.appointment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppointmentViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is APPOINTMENT Fragment"
    }
    val text: LiveData<String> = _text
}