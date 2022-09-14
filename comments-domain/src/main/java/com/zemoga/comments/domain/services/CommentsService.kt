package com.zemoga.comments.domain.services

import com.zemoga.author.entities.CommentResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Represents the available web services for comments.
 *
 */
interface CommentsService {

  @GET("comments")
  suspend fun fetchComments(): Response<List<CommentResponse>>
}
