package com.meli.items.domain.services

import com.app.core.network.BaseApiResponse
import javax.inject.Inject

class ItemsRemoteDataSource @Inject constructor(private val itemsService: ItemsService) :
  BaseApiResponse() {

  suspend fun searchItems(start: Int, limit: Int) =
    safeApiCall { itemsService.searchItems("motorola", start, limit) }
}
