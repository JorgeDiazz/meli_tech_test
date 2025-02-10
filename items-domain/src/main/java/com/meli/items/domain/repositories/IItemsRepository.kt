package com.meli.items.domain.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.meli.items.domain.data.Item
import com.meli.items.domain.repositories.ItemsMediator.Companion.DEFAULT_PAGE_SIZE
import kotlinx.coroutines.flow.Flow

/**
 * Represents the interface repository for items.
 *
 */
@ExperimentalPagingApi
interface IItemsRepository {
  fun searchItems(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<Item>>

  private fun getDefaultPageConfig(): PagingConfig {
    return PagingConfig(
      pageSize = DEFAULT_PAGE_SIZE,
      enablePlaceholders = false,
      prefetchDistance = DEFAULT_PAGE_SIZE / 4,
      initialLoadSize = DEFAULT_PAGE_SIZE,
    )
  }
}
