package com.zemoga.posts.domain.usecases

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.app.base.interfaces.FlowUseCase
import com.app.base.interfaces.Logger
import com.zemoga.posts.domain.data.Post
import com.zemoga.posts.domain.repositories.IPostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Represents the use case that gets all posts.
 *
 */
@ExperimentalPagingApi
class GetPostsUseCase @Inject constructor(
    private val postsRepository: IPostsRepository,
) : FlowUseCase<Unit, PagingData<Post>>() {

    override suspend fun execute(input: Unit): Flow<PagingData<Post>> {
        return postsRepository.getPosts()
    }
}