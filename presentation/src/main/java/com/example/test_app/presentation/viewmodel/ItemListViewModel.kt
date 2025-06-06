package com.example.test_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_app.domain.model.Item
import com.example.test_app.domain.usecase.GetAllItemsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemListViewModel(
    private val getAllItemsUseCase: GetAllItemsUseCase
) : ViewModel() {

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items

    init {
        viewModelScope.launch {
            _items.value = getAllItemsUseCase()
        }
    }
}