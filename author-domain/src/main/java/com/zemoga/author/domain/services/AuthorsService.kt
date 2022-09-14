package com.zemoga.author.domain.services

import com.zemoga.author.entities.AuthorResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Represents the available web services for users.
 *
 */
interface AuthorsService {

  @GET("users")
  suspend fun fetchAuthors(): Response<List<AuthorResponse>>
}
