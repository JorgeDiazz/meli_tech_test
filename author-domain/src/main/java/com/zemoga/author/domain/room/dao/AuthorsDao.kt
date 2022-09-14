package com.zemoga.author.domain.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zemoga.author.domain.data.Author

/**
 * Represents Room dao for authors entities.
 *
 */
@Dao
interface AuthorsDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(authors: List<Author>)

  @Query("SELECT * FROM author")
  fun getAllAuthors(): List<Author>
}
