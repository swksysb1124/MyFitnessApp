package com.example.myfitnessapp.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfitnessapp.event.Event
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // used to communicate event between each screen
    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _shouldBottomNaviShown = MutableStateFlow(true)
    val shouldBottomNaviShown: StateFlow<Boolean> = _shouldBottomNaviShown

    fun sentEvent(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    fun showOrHideBottomNavigationBar(shown: Boolean) {
        _shouldBottomNaviShown.value = shown
    }
}

