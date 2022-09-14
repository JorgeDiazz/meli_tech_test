package com.zemoga.posts.domain.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zemoga.posts.entities.PostResponse

@Entity(tableName = "post")
data class Post(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "body") val body: String,
    @ColumnInfo(name = "favorite") val favorite: Boolean,
)

fun List<PostResponse>.toBaseModel(): List<Post> = map { it.toBaseModel() }
fun PostResponse.toBaseModel(): Post = Post(id, userId, title, body, favorite = false)