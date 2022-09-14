package com.zemoga.comments.domain.services

import com.app.core.network.BaseApiResponse
import javax.inject.Inject

class CommentsRemoteDataSource @Inject constructor(private val commentsService: CommentsService) : BaseApiResponse() {

  suspend fun fetchComments() = safeApiCall { commentsService.fetchComments() }
}
