package com.zemoga.posts.domain.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.app.base.interfaces.Cache
import com.app.base.interfaces.Logger
import com.app.base.others.READ_POSTS_FROM_REMOTE_KEY
import com.app.core.network.NetworkState
import com.zemoga.posts.domain.data.Post
import com.zemoga.posts.domain.data.PostsDatabase
import com.zemoga.posts.domain.data.toBaseModel
import com.zemoga.posts.domain.services.PostsRemoteDataSource
import com.zemoga.posts.entities.PostResponse
import com.zemoga.posts.entities.RemoteKeysRoom

/**
 * Represents the mediator between remote and local posts.
 *
 */
@ExperimentalPagingApi
class PostsMediator(private val logger: Logger, private val cache: Cache, private val postsRemoteDataSource: PostsRemoteDataSource, private val appDatabase: PostsDatabase) :
    RemoteMediator<Int, Post>() {

    companion object {
        const val DEFAULT_PAGE_INDEX = 0
        const val DEFAULT_PAGE_SIZE = 25
    }

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, Post>
    ): MediatorResult {
        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as? Int ?: 0
            }
        }

        try {
            val readPostsFromRemoteKey = cache.readBoolean(READ_POSTS_FROM_REMOTE_KEY, true)

            val postsResponseNetworkState: NetworkState<List<PostResponse>> = if (readPostsFromRemoteKey) {
                postsRemoteDataSource.fetchPosts(page, state.config.pageSize)
            } else {
                appDatabase.getRepoDao().clearRemoteKeys()

                NetworkState.Success(emptyList())
            }

            if (postsResponseNetworkState is NetworkState.Error) {
                throw postsResponseNetworkState.exception!!
            }

            val postsResponseList = postsResponseNetworkState.data ?: emptyList()
            val endOfPagination = postsResponseList.isEmpty()

            appDatabase.withTransaction {
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - DEFAULT_PAGE_SIZE
                val nextKey = if (endOfPagination) null else page + DEFAULT_PAGE_SIZE

                val keys = postsResponseList.map {
                    RemoteKeysRoom(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }

                appDatabase.getRepoDao().insertAll(keys)
                appDatabase.getPostsDao().insertAll(postsResponseList.toBaseModel())
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPagination)
        } catch (exception: Exception) {
            logger.e(exception.message.orEmpty(), exception)
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, Post>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(DEFAULT_PAGE_SIZE) ?: DEFAULT_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)

                remoteKeys?.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)

                remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, Post>): RemoteKeysRoom? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { post -> appDatabase.getRepoDao().remoteKeysPostId(post.id) }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Post>): RemoteKeysRoom? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { post -> appDatabase.getRepoDao().remoteKeysPostId(post.id) }
    }

    private suspend fun getClosestRemoteKey(state: PagingState<Int, Post>): RemoteKeysRoom? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                appDatabase.getRepoDao().remoteKeysPostId(repoId)
            }
        }
    }
}