package com.zemoga.posts.domain.usecases

import androidx.paging.ExperimentalPagingApi
import com.app.base.interfaces.FlowUseCase
import com.zemoga.posts.domain.repositories.IPostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Represents the use case that deletes non-favorite posts.
 *
 */
@ExperimentalPagingApi
class DeleteNonFavoritePostsUseCase @Inject constructor(
    private val postsRepository: IPostsRepository,
) : FlowUseCase<Unit, Boolean>() {

    override suspend fun execute(input: Unit): Flow<Boolean> {
        return postsRepository.deleteNonFavoritePosts()
    }
}