package studio.jasonsu.myfitness.ui.screen.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class LoginViewModel : ViewModel() {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name
    val isNameExceedMaxLength: LiveData<Boolean> =
        name.map { it.length > MAX_NAME_LENGTH }

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

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