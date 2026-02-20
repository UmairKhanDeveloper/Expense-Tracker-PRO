package com.example.expensetrackerpro.firebase

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel( private val repo: AuthRepository) : ViewModel() {


    private val _isUserLoggedIn = MutableStateFlow(true)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn.asStateFlow()

    fun createUser(authUser: AuthUser) = repo.createUser(authUser)

    fun loginUser(authUser: AuthUser) = repo.loginUser(authUser)

    fun createUserWithPhone(mobile: String, activity: Activity) =
        repo.createUserWithPhone(mobile, activity)

    fun signInWithCredential(code: String) = repo.signWithCredential(code)

    fun logoutUser() {
        viewModelScope.launch {
            repo.logoutUser().collect { result ->
                when (result) {
                    is ResultState.Success -> _isUserLoggedIn.value = false
                    is ResultState.Error -> _isUserLoggedIn.value = true
                    is ResultState.Loading -> {}
                }
            }
        }
    }

    fun checkUserLoginStatus() {
        viewModelScope.launch {
            repo.logoutUser().collect { result ->
                _isUserLoggedIn.value = result is ResultState.Error
            }
        }
    }
}