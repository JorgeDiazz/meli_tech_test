package com.meli.items.domain.services

import com.app.core.network.BaseApiResponse
import javax.inject.Inject

class ItemsRemoteDataSource @Inject constructor(private val itemsService: ItemsService) :
  BaseApiResponse() {

  suspend fun searchItems(query: String, offset: Int, limit: Int) =
    safeApiCall { itemsService.searchItems(query, offset, limit) }
}
