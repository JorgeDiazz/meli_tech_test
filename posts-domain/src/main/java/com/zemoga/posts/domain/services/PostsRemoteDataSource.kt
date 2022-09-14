package com.zemoga.posts.domain.services

import com.app.core.network.BaseApiResponse
import javax.inject.Inject

class PostsRemoteDataSource @Inject constructor(private val postsService: PostsService) : BaseApiResponse() {

    suspend fun fetchPosts(start: Int, limit: Int) = safeApiCall { postsService.fetchPosts(start, limit) }
}