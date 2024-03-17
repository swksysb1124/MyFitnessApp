package com.example.myfitnessapp.ui.screen.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfitnessapp.model.Profile
import com.example.myfitnessapp.repository.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _heightInCm = MutableLiveData<Int>()
    val heightInCm: LiveData<Int> = _heightInCm

    private val _weightInKg = MutableLiveData<Int>()
    val weightInKg: LiveData<Int> = _weightInKg

    private val _editDialogConfig = MutableLiveData<ProfileEditDialogConfig>()
    val editDialogConfig: LiveData<ProfileEditDialogConfig> = _editDialogConfig

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
                    val weight = _weightInKg.value?.takeIf { it > 0 } ?: return@launch
                    profileRepository.updateProfile(
                        Profile.createPrimaryProfile(updateValue, weight)
                    )
                    _heightInCm.value = updateValue
                }

                ProfileEditType.Weight -> {
                    val height = _heightInCm.value?.takeIf { it > 0 } ?: return@launch
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