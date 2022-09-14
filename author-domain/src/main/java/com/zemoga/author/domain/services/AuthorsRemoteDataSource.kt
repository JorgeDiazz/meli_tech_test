package com.zemoga.author.domain.services

import com.app.core.network.BaseApiResponse
import javax.inject.Inject

class AuthorsRemoteDataSource @Inject constructor(private val authorsService: AuthorsService) : BaseApiResponse() {

  suspend fun fetchAuthors() = safeApiCall { authorsService.fetchAuthors() }
}
