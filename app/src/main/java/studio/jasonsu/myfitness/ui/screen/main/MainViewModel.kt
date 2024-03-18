package studio.jasonsu.myfitness.ui.screen.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import studio.jasonsu.myfitness.event.Event

class MainViewModel : ViewModel() {
    // used to communicate event between each screen
    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _shouldBottomNaviShown = MutableStateFlow(true)
    val shouldBottomNaviShown: StateFlow<Boolean> = _shouldBottomNaviShown

    private val _isReady = MutableLiveData(false)
    val isReady: LiveData<Boolean> = _isReady

    init {
        viewModelScope.launch {
            // TODO can define when will isReady emit true
            delay(1500)
            _isReady.value = true
        }
    }

    fun sentEvent(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    fun showOrHideBottomNavigationBar(shown: Boolean) {
        _shouldBottomNaviShown.value = shown
    }
}

