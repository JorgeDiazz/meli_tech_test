package com.zemoga.comments.domain.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zemoga.comments.domain.data.Comment

/**
 * Represents Room dao for comments entities.
 *
 */
@Dao
interface CommentsDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(authors: List<Comment>)

  @Query("SELECT * FROM comment")
  fun getAllComments(): List<Comment>
}
