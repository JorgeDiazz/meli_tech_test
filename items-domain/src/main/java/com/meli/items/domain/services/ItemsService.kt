package com.meli.items.domain.services

import com.meli.items.entities.SearchItemsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Represents the available web services for items.
 *
 */
interface ItemsService {

  @GET("search")
  suspend fun searchItems(
    @Query("q") query: String,
    @Query("offset") offset: Int,
    @Query("limit") limit: Int
  ): Response<SearchItemsResponse>
}
