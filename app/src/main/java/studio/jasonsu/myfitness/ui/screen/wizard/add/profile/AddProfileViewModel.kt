package studio.jasonsu.myfitness.ui.screen.wizard.add.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import studio.jasonsu.myfitness.model.Profile
import studio.jasonsu.myfitness.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class AddProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {

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
        val height = height.value.toIntOrNull() ?: return
        val weight = weight.value.toIntOrNull() ?: return
        viewModelScope.launch {
            profileRepository.saveProfile(
                Profile.createPrimaryProfile(height, weight)
            )
            onComplete()
        }
    }
}