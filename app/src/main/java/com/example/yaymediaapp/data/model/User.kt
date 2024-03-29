package com.example.yaymediaapp.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val id: String,
    val username: String,
    val name: String,
    val imageUrl: String
) {
    @Ignore
    val isValid =
        id.isNotEmpty() && username.isNotEmpty() && name.isNotEmpty() && imageUrl.isNotEmpty()
}