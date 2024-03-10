package com.example.myfitnessapp.ui.screen.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfitnessapp.repository.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _heightInCm = MutableLiveData<Double>()
    val heightInCm: LiveData<Double> = _heightInCm

    private val _weightInKg = MutableLiveData<Double>()
    val weightInKg: LiveData<Double> = _weightInKg

    init {
        viewModelScope.launch {
            val profile = profileRepository.getProfile()
            _heightInCm.value = profile.height
            _weightInKg.value = profile.weight
        }
    }
}