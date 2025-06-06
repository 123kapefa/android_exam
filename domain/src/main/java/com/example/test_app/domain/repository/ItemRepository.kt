package com.example.test_app.domain.repository

import com.example.test_app.domain.model.Item

interface ItemRepository {
    suspend fun getAllItems(): List<Item>
}