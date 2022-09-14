package com.zemoga.author.domain.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zemoga.author.domain.room.converters.RoomConverters
import com.zemoga.author.domain.room.dao.AuthorsDao
import kotlinx.serialization.ExperimentalSerializationApi

@Database(version = 1, entities = [Author::class], exportSchema = false)
@TypeConverters(RoomConverters::class)
@ExperimentalSerializationApi
abstract class AuthorsDatabase : RoomDatabase() {

  abstract fun getAuthorsDao(): AuthorsDao
}
