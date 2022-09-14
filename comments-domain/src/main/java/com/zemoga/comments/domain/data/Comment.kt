package com.zemoga.comments.domain.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zemoga.author.entities.CommentResponse

@Entity(tableName = "comment")
data class Comment(
  @ColumnInfo(name = "postId")
  val postId: Int,
  @PrimaryKey @ColumnInfo(name = "id")
  val id: Int,
  @ColumnInfo(name = "name")
  val name: String,
  @ColumnInfo(name = "email")
  val email: String,
  @ColumnInfo(name = "body")
  val body: String,
)

fun List<CommentResponse>.toBaseModel(): List<Comment> = map { it.toBaseModel() }
fun CommentResponse.toBaseModel(): Comment = Comment(postId, id, name, email, body)
