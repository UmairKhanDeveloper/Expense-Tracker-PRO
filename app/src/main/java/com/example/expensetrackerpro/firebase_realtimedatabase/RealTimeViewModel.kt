package com.example.expensetrackerpro.firebase_realtimedatabase

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerpro.firebase.ResultState
import kotlinx.coroutines.launch

class RealTimeViewModel(private val repository: RealTimeRepository) : ViewModel() {

    private val _res: MutableState<ItemsState> = mutableStateOf(ItemsState())
    val res: State<ItemsState> = _res

    fun insert(item: RealTimeUser.RealTimeItems) = repository.insert(item)

    init {
        viewModelScope.launch {
            repository.getItems().collect { result ->
                when (result) {
                    is ResultState.Error -> _res.value = ItemsState(error = result.error.message ?: "Unknown error")
                    ResultState.Loading -> _res.value = ItemsState(isLoading = true)
                    is ResultState.Success -> _res.value = ItemsState(items = result.response)
                }
            }
        }
    }

    fun delete(key: String) = repository.delete(key)
    fun update(item: RealTimeUser) = repository.update(item)
}

data class ItemsState(
    val items: List<RealTimeUser> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)
