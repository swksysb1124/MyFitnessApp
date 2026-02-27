package studio.jasonsu.myfitness.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class LoginViewModel : ViewModel() {

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()
    val isNameExceedMaxLength = name.map { it.length > MAX_NAME_LENGTH }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false
        )

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber = _phoneNumber.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    fun onNameChanged(name: String) {
        _name.value = name
    }

    fun onEmailChanged(name: String) {
        _email.value = name
    }

    fun onPhoneNumberChanged(name: String) {
        _phoneNumber.value = name
    }

    companion object {
        const val MAX_NAME_LENGTH = 20
    }
}