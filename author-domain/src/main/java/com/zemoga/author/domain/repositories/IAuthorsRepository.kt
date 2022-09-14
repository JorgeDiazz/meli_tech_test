package com.zemoga.author.domain.repositories

import com.zemoga.author.domain.data.AuthorsState
import kotlinx.coroutines.flow.Flow

/**
 * Represents the interface repository for authors.
 *
 */
interface IAuthorsRepository {
    fun getAuthors(): Flow<AuthorsState>
}
