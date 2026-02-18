package com.example.expensetrackerpro.google_firebase

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)