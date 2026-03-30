package com.example.expensetrackerpro.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.expensetrackerpro.data.local.entity.IncomeEntity

@Dao
interface IncomeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(income: IncomeEntity)

    @Query("SELECT * FROM income_table ORDER BY id DESC")
    fun getAllIncome(): LiveData<List<IncomeEntity>>

    @Update
    suspend fun update(income: IncomeEntity)

    @Delete
    suspend fun delete(income: IncomeEntity)
}