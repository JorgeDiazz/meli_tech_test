package com.zemoga.author.domain.usecases

import com.app.base.interfaces.FlowUseCase
import com.zemoga.author.domain.data.AuthorsState
import com.zemoga.author.domain.repositories.IAuthorsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Represents the use case that gets all authors.
 *
 */
class GetAuthorsUseCase @Inject constructor(
  private val authorsRepository: IAuthorsRepository,
) : FlowUseCase<Unit, AuthorsState>() {

  override suspend fun execute(input: Unit): Flow<AuthorsState> {
    return authorsRepository.getAuthors()
  }
}
