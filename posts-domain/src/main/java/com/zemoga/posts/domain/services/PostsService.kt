package com.zemoga.posts.domain.services

import com.zemoga.posts.entities.PostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Represents the available web services for posts.
 *
 */
interface PostsService {

    @GET("posts")
    suspend fun fetchPosts(@Query("_start") start: Int, @Query("_limit") limit: Int): Response<List<PostResponse>>
}