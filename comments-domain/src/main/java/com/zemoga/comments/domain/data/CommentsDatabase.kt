package com.zemoga.comments.domain.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zemoga.comments.domain.room.dao.CommentsDao
import kotlinx.serialization.ExperimentalSerializationApi

@Database(version = 1, entities = [Comment::class], exportSchema = false)
@ExperimentalSerializationApi
abstract class CommentsDatabase : RoomDatabase() {

  abstract fun getCommentsDao(): CommentsDao
}
