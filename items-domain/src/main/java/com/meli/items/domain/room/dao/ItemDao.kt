package com.meli.items.domain.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.meli.items.domain.data.Item

@Dao
interface ItemDao {
  @Transaction
  @Query("SELECT * FROM items ORDER BY id ASC")
  fun getPagingSource(): PagingSource<Int, Item>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertItems(items: List<Item>)

  @Query("DELETE FROM items")
  suspend fun clearAllItems()
}
