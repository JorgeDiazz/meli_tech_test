package com.zemoga.comments.domain.usecases

import com.app.base.interfaces.FlowUseCase
import com.zemoga.comments.domain.data.CommentsState
import com.zemoga.comments.domain.repositories.ICommentsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Represents the use case that gets all comments.
 *
 */
class GetCommentsUseCase @Inject constructor(
  private val commentsRepository: ICommentsRepository,
) : FlowUseCase<Unit, CommentsState>() {

  override suspend fun execute(input: Unit): Flow<CommentsState> {
    return commentsRepository.getComments()
  }
}
