package com.example.mediatore.ui.appointment_create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppointmentCreateViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is APPOINTMENT Fragment"
    }
    val text: LiveData<String> = _text
}