package com.example.yaymediaapp.data.model
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

@Entity
data class Post(
    @PrimaryKey
    val id: String,
    val imageUrl: String,
    val description: String?,
    val date: Date,
    val userId: String,
)


data class PostWithData(
    @Embedded val post: Post,

    @Relation(
        parentColumn = "userId",
        entityColumn = "id"
    )
    val user: User,


    @Relation(
        entity = Like::class,
        parentColumn = "id",
        entityColumn = "postId"
    )
    val likes: List<LikeWithUser>
)

data class PostToPublish(
    val imageUrl: String,
    val user: User,
)
