package com.bartekjobhunt.grazerproject.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartekjobhunt.grazerproject.repository.GrazerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: GrazerRepository
) :
    ViewModel() {

    var email by mutableStateOf("")
        private set

    fun updateEmail(input: String) {
        email = input
    }

    var password by mutableStateOf("")
        private set

    fun updatePassword(input: String) {
        password = input
    }

    var passwordVisible by mutableStateOf(false)
        private set

    fun updatePasswordVisible(input: Boolean) {
        passwordVisible = input
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            _loginState.value = LoginState.Loading
            try {
                if (repository.login(email, password)) {
                    _loginState.value = LoginState.Success
                } else { // just for simplicity, in real app we'd have more specific handling of login response
                    _loginState.value = LoginState.Error("Invalid credentials")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}

sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data object Success : LoginState()
    data class Error(val message: String) : LoginState()
}
