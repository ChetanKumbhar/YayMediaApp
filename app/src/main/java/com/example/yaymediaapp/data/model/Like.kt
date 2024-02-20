package com.example.yaymediaapp.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date


@Entity
data class Like(
    @PrimaryKey
    val id: String,
    val date: Date,
    val userId: String,
    val postId: String
)

data class LikeWithUser(
    @Embedded val like: Like,
    @Relation(
        parentColumn = "userId",
        entityColumn = "id"
    )
    val user: User
)
