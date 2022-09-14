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
 * Represents the use case that deletes a posts.
 *
 */
@ExperimentalPagingApi
class DeletePostUseCase @Inject constructor(
    private val postsRepository: IPostsRepository,
) : FlowUseCase<Int, Boolean>() {

    override suspend fun execute(input: Int): Flow<Boolean> {
        return postsRepository.deletePost(input)
    }
}