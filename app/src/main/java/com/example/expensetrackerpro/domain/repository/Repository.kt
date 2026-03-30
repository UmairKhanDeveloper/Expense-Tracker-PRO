package com.example.expensetrackerpro.domain.repository

import com.example.expensetrackerpro.data.local.database.AppDatabase
import com.example.expensetrackerpro.data.local.entity.ExpenseEntity
import com.example.expensetrackerpro.data.local.entity.IncomeEntity

class Repository(private val db: AppDatabase) {

    // Income
    fun getAllIncome() = db.incomeDao().getAllIncome()

    suspend fun insertIncome(income: IncomeEntity) {
        db.incomeDao().insert(income)
    }

    suspend fun updateIncome(income: IncomeEntity) {
        db.incomeDao().update(income)
    }

    suspend fun deleteIncome(income: IncomeEntity) {
        db.incomeDao().delete(income)
    }

    // Expense
    fun getAllExpense() = db.expenseDao().getAllExpense()

    suspend fun insertExpense(expense: ExpenseEntity) {
        db.expenseDao().insert(expense)
    }

    suspend fun updateExpense(expense: ExpenseEntity) {
        db.expenseDao().update(expense)
    }

    suspend fun deleteExpense(expense: ExpenseEntity) {
        db.expenseDao().delete(expense)
    }
}