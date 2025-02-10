package com.meli.items.domain.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.base.interfaces.Cache
import com.app.base.interfaces.Logger
import com.app.core.network.BaseApiResponse
import com.meli.items.domain.data.Item
import com.meli.items.domain.data.ItemsDatabase
import com.meli.items.domain.services.ItemsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Represents the repository for items.
 *
 */
@ExperimentalPagingApi
class ItemsRepository @Inject constructor(
  private val logger: Logger,
  private val cache: Cache,
  private val remoteDataSource: ItemsRemoteDataSource,
  private val itemsDatabase: ItemsDatabase,
) : BaseApiResponse(), IItemsRepository {

    override fun searchItems(query: String, pagingConfig: PagingConfig): Flow<PagingData<Item>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { itemsDatabase.getItemsDao().getPagingSource() },
            remoteMediator = ItemsMediator(logger, cache, remoteDataSource, itemsDatabase, query),
        ).flow
    }
}
