package com.example.myfitnessapp.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.myfitnessapp.repository.LoginRepository

class UserNameUseCase(
    private val loginRepository: LoginRepository
) {
    var data by mutableStateOf("")
        private set
}
