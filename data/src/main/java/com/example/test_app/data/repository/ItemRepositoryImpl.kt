package com.example.test_app.data.repository

import com.example.test_app.domain.model.Item
import com.example.test_app.domain.repository.ItemRepository

class ItemRepositoryImpl : ItemRepository {
    override suspend fun getAllItems(): List<Item> {
        return listOf(
            Item(1, "Sad Onion", "Tears up", "https://example.com/sad_onion.png"),
            Item(2, "Inner Eye", "Triple shot", "https://example.com/inner_eye.png")
        )
    }
}