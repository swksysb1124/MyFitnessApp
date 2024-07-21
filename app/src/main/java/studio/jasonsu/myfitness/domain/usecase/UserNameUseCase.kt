package studio.jasonsu.myfitness.domain.usecase

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import studio.jasonsu.myfitness.repository.LoginRepository

class UserNameUseCase(
    private val loginRepository: LoginRepository
) {
    var data by mutableStateOf("")
        private set
}
