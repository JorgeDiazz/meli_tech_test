package com.meli.items.domain.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.app.base.interfaces.Cache
import com.app.base.interfaces.Logger
import com.app.base.others.READ_ITEMS_FROM_REMOTE_KEY
import com.app.core.network.NetworkState
import com.meli.items.domain.data.Item
import com.meli.items.domain.data.ItemsDatabase
import com.meli.items.domain.data.toBaseModel
import com.meli.items.domain.services.ItemsRemoteDataSource
import com.meli.items.entities.RemoteKeysRoom
import com.meli.items.entities.SearchItemsResponse

/**
 * Represents the mediator between remote and local items.
 *
 */
@ExperimentalPagingApi
class ItemsMediator(
  private val logger: Logger,
  private val cache: Cache,
  private val itemsRemoteDataSource: ItemsRemoteDataSource,
  private val appDatabase: ItemsDatabase,
  private val query: String
) : RemoteMediator<Int, Item>() {

  companion object {
    const val DEFAULT_PAGE_INDEX = 0
    const val DEFAULT_PAGE_SIZE = 25
  }

  override suspend fun load(
    loadType: LoadType,
    state: PagingState<Int, Item>
  ): MediatorResult {
    val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
      is MediatorResult.Success -> {
        return pageKeyData
      }
      else -> {
        pageKeyData as? Int ?: 0
      }
    }

    try {
      val readItemsFromRemoteKey = cache.readBoolean(READ_ITEMS_FROM_REMOTE_KEY, true)

      val itemsResponseNetworkState: NetworkState<SearchItemsResponse> = if (readItemsFromRemoteKey) {
        if (loadType == LoadType.REFRESH) {
          appDatabase.withTransaction {
            appDatabase.getRepoDao().clearRemoteKeys()
            appDatabase.getItemsDao().clearAllItems()
          }
        }

        itemsRemoteDataSource.searchItems(query, page, state.config.pageSize)
      } else {
        appDatabase.getRepoDao().clearRemoteKeys()
        NetworkState.Success(SearchItemsResponse(resultXES = listOf()))
      }

      if (itemsResponseNetworkState is NetworkState.Error) {
        throw itemsResponseNetworkState.exception!!
      }

      val itemsResponseList = itemsResponseNetworkState.data?.resultXES ?: emptyList()
      val endOfPagination = itemsResponseList.isEmpty()

      appDatabase.withTransaction {
        val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - DEFAULT_PAGE_SIZE
        val nextKey = if (endOfPagination) null else page + DEFAULT_PAGE_SIZE

        val keys = itemsResponseList.map {
          RemoteKeysRoom(repoId = it.id ?: "", prevKey = prevKey, nextKey = nextKey)
        }

        appDatabase.getRepoDao().insertAll(keys)
        appDatabase.getItemsDao().insertItems(itemsResponseList.toBaseModel())
      }

      return MediatorResult.Success(endOfPaginationReached = endOfPagination)
    } catch (exception: Exception) {
      logger.e(exception.message.orEmpty(), exception)
      return MediatorResult.Error(exception)
    }
  }

  private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, Item>): Any? {
    return when (loadType) {
      LoadType.REFRESH -> {
        val remoteKeys = getClosestRemoteKey(state)
        remoteKeys?.nextKey?.minus(DEFAULT_PAGE_SIZE) ?: DEFAULT_PAGE_INDEX
      }
      LoadType.APPEND -> {
        val remoteKeys = getLastRemoteKey(state)

        remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
      }
      LoadType.PREPEND -> {
        val remoteKeys = getFirstRemoteKey(state)

        remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
        remoteKeys.prevKey
      }
    }
  }

  private suspend fun getFirstRemoteKey(state: PagingState<Int, Item>): RemoteKeysRoom? {
    return state.pages
      .firstOrNull { it.data.isNotEmpty() }
      ?.data?.firstOrNull()
      ?.let { item -> appDatabase.getRepoDao().remoteKeysItemId(item.id) }
  }

  private suspend fun getLastRemoteKey(state: PagingState<Int, Item>): RemoteKeysRoom? {
    return state.pages
      .lastOrNull { it.data.isNotEmpty() }
      ?.data?.lastOrNull()
      ?.let { item -> appDatabase.getRepoDao().remoteKeysItemId(item.id) }
  }

  private suspend fun getClosestRemoteKey(state: PagingState<Int, Item>): RemoteKeysRoom? {
    return state.anchorPosition?.let { position ->
      state.closestItemToPosition(position)?.id?.let { repoId ->
        appDatabase.getRepoDao().remoteKeysItemId(repoId)
      }
    }
  }
}
