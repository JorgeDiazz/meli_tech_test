package com.zemoga.posts.domain.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zemoga.posts.domain.room.dao.PostsDao
import com.zemoga.posts.domain.room.dao.RemoteKeysDao
import com.zemoga.posts.entities.RemoteKeysRoom

@Database(version = 1, entities = [Post::class, RemoteKeysRoom::class], exportSchema = false)
abstract class PostsDatabase : RoomDatabase() {

    abstract fun getRepoDao(): RemoteKeysDao
    abstract fun getPostsDao(): PostsDao
}
