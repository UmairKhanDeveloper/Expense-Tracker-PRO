package com.example.expensetrackerpro.domain.usecase

sealed class ResultState<out T> {

    object Loading : ResultState<Nothing>()

    data class Success<T>(val data: T) : ResultState<T>()

    data class Error(val exception: Throwable) : ResultState<Nothing>()
}