package studio.jasonsu.myfitness.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import studio.jasonsu.myfitness.model.Profile
import studio.jasonsu.myfitness.repository.ProfileRepository

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _heightInCm = MutableStateFlow(0)
    val heightInCm = _heightInCm.asStateFlow()

    private val _weightInKg = MutableStateFlow(0)
    val weightInKg = _weightInKg.asStateFlow()

    private val _editDialogConfig = MutableStateFlow(
        ProfileEditDialogConfig(
            ProfileEditType.Height,
            isDialogOpen = false
        )
    )
    val editDialogConfig = _editDialogConfig.asStateFlow()

    init {
        viewModelScope.launch {
            val profile = profileRepository.getProfile() ?: Profile(1, 170, 70)
            _heightInCm.value = profile.height
            _weightInKg.value = profile.weight
        }
    }

    fun openDialog(value: String, type: ProfileEditType) {
        _editDialogConfig.value = ProfileEditDialogConfig(
            type = type,
            value = value,
            isDialogOpen = true
        )
    }

    fun closeDialog(type: ProfileEditType) {
        _editDialogConfig.value = ProfileEditDialogConfig(
            type = type,
            isDialogOpen = false
        )
    }

    fun onSaved(
        type: ProfileEditType,
        value: String
    ) {
        val updateValue = value.toIntOrNull() ?: return
        viewModelScope.launch {
            when (type) {
                ProfileEditType.Height -> {
                    val weight = _weightInKg.value.takeIf { it > 0 } ?: return@launch
                    profileRepository.updateProfile(
                        Profile.createPrimaryProfile(updateValue, weight)
                    )
                    _heightInCm.value = updateValue
                }

                ProfileEditType.Weight -> {
                    val height = _heightInCm.value.takeIf { it > 0 } ?: return@launch
                    profileRepository.updateProfile(
                        Profile.createPrimaryProfile(height, updateValue)
                    )
                    _weightInKg.value = updateValue
                }
            }
        }
    }
}

data class ProfileEditDialogConfig(
    val type: ProfileEditType,
    val value: String? = null,
    val isDialogOpen: Boolean
)