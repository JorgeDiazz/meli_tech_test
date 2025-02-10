package com.meli.items.domain.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.meli.items.domain.data.converters.RoomConverters
import com.meli.items.domain.room.dao.ItemDao
import com.meli.items.domain.room.dao.RemoteKeysDao
import com.meli.items.entities.RemoteKeysRoom
import kotlinx.serialization.ExperimentalSerializationApi

@Database(
  version = 1, entities = [Item::class, RemoteKeysRoom::class], exportSchema = false
)
@TypeConverters(RoomConverters::class)
@ExperimentalSerializationApi
abstract class ItemsDatabase : RoomDatabase() {

  abstract fun getRepoDao(): RemoteKeysDao
  abstract fun getItemsDao(): ItemDao
}
