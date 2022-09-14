package com.zemoga.posts.domain.usecases

import androidx.paging.ExperimentalPagingApi
import com.app.base.interfaces.FlowUseCase
import com.zemoga.posts.domain.repositories.IPostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Represents the use case that updates favorite field in posts.
 *
 */
@ExperimentalPagingApi
class UpdateFavoritePostUseCase @Inject constructor(
    private val postsRepository: IPostsRepository,
) : FlowUseCase<Pair<@JvmSuppressWildcards Int, @JvmSuppressWildcards Boolean>, Boolean>() {

    override suspend fun execute(input: Pair<Int, Boolean>): Flow<Boolean> {
        val (postId, favorite) = input
        return postsRepository.updateFavoritePost(postId, favorite)
    }
}