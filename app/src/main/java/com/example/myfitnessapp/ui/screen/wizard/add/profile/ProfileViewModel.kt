package com.example.myfitnessapp.ui.screen.wizard.add.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine

class ProfileViewModel : ViewModel() {

    private val _height = MutableStateFlow("")
    val height: StateFlow<String> = _height

    private val _weight = MutableStateFlow("")
    val weight: StateFlow<String> = _weight

    val isFieldsAllValid = height.combine(weight) { height, weight ->
        height.isNotEmpty() && height != "0"
                && weight.isNotEmpty() && weight != "0"
    }

    fun onHeightChange(height: String) {
        _height.value = height
    }

    fun onWightChange(weight: String) {
        _weight.value = weight
    }

    fun save(onComplete: () -> Unit) {
        Log.d("ProfileViewModel", "save: height=${height.value}, weight=${weight.value}")
        // TODO insert profile in database
    }
}