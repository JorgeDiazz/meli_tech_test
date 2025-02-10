package com.meli.items.domain.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.meli.items.entities.RemoteKeysRoom
/**
 * Represents Room dao for remote keys entities.
 *
 */
@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(remoteKey: List<RemoteKeysRoom>)

    @Query("SELECT * FROM remote_keys WHERE repo_id = :id")
    suspend fun remoteKeysItemId(id: Int): RemoteKeysRoom

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}
