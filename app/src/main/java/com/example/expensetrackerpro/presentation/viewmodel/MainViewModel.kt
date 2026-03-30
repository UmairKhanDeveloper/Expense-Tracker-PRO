package com.example.expensetrackerpro.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerpro.data.local.entity.ExpenseEntity
import com.example.expensetrackerpro.data.local.entity.IncomeEntity
import com.example.expensetrackerpro.domain.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    // Income
    val allIncome = repository.getAllIncome()

    fun insertIncome(income: IncomeEntity) = viewModelScope.launch {
        repository.insertIncome(income)
    }

    fun updateIncome(income: IncomeEntity) = viewModelScope.launch {
        repository.updateIncome(income)
    }

    fun deleteIncome(income: IncomeEntity) = viewModelScope.launch {
        repository.deleteIncome(income)
    }

    // Expense
    val allExpense = repository.getAllExpense()

    fun insertExpense(expense: ExpenseEntity) = viewModelScope.launch {
        repository.insertExpense(expense)
    }

    fun updateExpense(expense: ExpenseEntity) = viewModelScope.launch {
        repository.updateExpense(expense)
    }

    fun deleteExpense(expense: ExpenseEntity) = viewModelScope.launch {
        repository.deleteExpense(expense)
    }
}