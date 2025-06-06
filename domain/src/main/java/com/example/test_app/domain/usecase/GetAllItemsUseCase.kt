package com.example.test_app.domain.usecase

import com.example.test_app.domain.repository.ItemRepository

class GetAllItemsUseCase(private val repository: ItemRepository) {
    suspend operator fun invoke() = repository.getAllItems()
}