package com.bartekjobhunt.grazerproject.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartekjobhunt.grazerproject.network.model.User
import com.bartekjobhunt.grazerproject.repository.GrazerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val repository: GrazerRepository) :
    ViewModel() {

    private val _usersState = MutableStateFlow<UsersState>(UsersState.Loading)
    val usersState: StateFlow<UsersState> = _usersState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val users = repository.getUsers()
                _usersState.value = UsersState.Success(users)
            } catch (e: Exception) {
                _usersState.value = UsersState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun calculateAge(dateOfBirthEpoch: Long): Int {
        val dateOfBirth =
            Instant.ofEpochSecond(dateOfBirthEpoch).atZone(ZoneId.systemDefault()).toLocalDate()
        val today = LocalDate.now()
        return Period.between(dateOfBirth, today).years
    }
}

sealed class UsersState {
    object Loading : UsersState()
    data class Success(val users: List<User>) : UsersState()
    data class Error(val message: String) : UsersState()
}
