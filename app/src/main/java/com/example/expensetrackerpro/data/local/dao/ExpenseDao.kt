package com.example.expensetrackerpro.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.expensetrackerpro.data.local.entity.ExpenseEntity

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: ExpenseEntity)

    @Query("SELECT * FROM expense_table ORDER BY id DESC")
    fun getAllExpense(): LiveData<List<ExpenseEntity>>

    @Update
    suspend fun update(expense: ExpenseEntity)

    @Delete
    suspend fun delete(expense: ExpenseEntity)
}