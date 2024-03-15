package com.example.myfitnessapp.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfitnessapp.event.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // used to communicate event between each screen
    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun sentEvent(event: Event) {
        viewModelScope.launch {
            //  don't sure why need to add a delay
            //  or MainScreen would not receive the flow change
            //  TODO find out why this happens
            delay(500)
            _eventFlow.emit(event)
        }
    }
}

