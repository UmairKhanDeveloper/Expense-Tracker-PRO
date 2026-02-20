package com.example.expensetrackerpro.firebase_realtimedatabase

import com.example.expensetrackerpro.firebase.ResultState
import kotlinx.coroutines.flow.Flow

interface RealTimeRepository {
    fun insert(item: RealTimeUser.RealTimeItems): Flow<ResultState<String>>
    fun getItems(): Flow<ResultState<List<RealTimeUser>>>
    fun delete(key: String): Flow<ResultState<String>>
    fun update(res: RealTimeUser): Flow<ResultState<String>>
}
