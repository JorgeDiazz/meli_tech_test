package com.zemoga.posts.domain.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zemoga.posts.domain.data.Post
import com.zemoga.posts.domain.repositories.PostsMediator.Companion.DEFAULT_PAGE_SIZE
import kotlinx.coroutines.flow.Flow

/**
 * Represents the interface repository for posts.
 *
 */
@ExperimentalPagingApi
interface IPostsRepository {
    fun getPosts(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<Post>>
    fun deletePost(postId: Int): Flow<Boolean>
    fun deleteNonFavoritePosts(): Flow<Boolean>
    fun updateFavoritePost(postId: Int, favorite: Boolean): Flow<Boolean>

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            enablePlaceholders = false,
            prefetchDistance = DEFAULT_PAGE_SIZE / 4,
            initialLoadSize = DEFAULT_PAGE_SIZE,
        )
    }
}