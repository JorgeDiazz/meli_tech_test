package com.meli.items.domain.usecases

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.app.base.interfaces.FlowUseCase
import com.meli.items.domain.data.Item
import com.meli.items.domain.repositories.IItemsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Represents the use case that fetches items.
 *
 */
@ExperimentalPagingApi
class SearchItemsUseCase @Inject constructor(
  private val itemsRepository: IItemsRepository,
) : FlowUseCase<String, PagingData<Item>>() {

  private val defaultSearch = "celular"
  override suspend fun execute(input: String): Flow<PagingData<Item>> {
    return itemsRepository.searchItems(query = input.ifEmpty { defaultSearch })
  }
}
