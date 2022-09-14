package com.zemoga.comments.domain.repositories

import com.zemoga.comments.domain.data.CommentsState
import kotlinx.coroutines.flow.Flow

/**
 * Represents the repository interface for comments.
 *
 */
interface ICommentsRepository {
    fun getComments(): Flow<CommentsState>
}
