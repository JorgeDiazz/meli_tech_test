package com.zemoga.comments.domain.usecases


import com.app.base.test
import com.zemoga.comments.domain.data.CommentsState
import com.zemoga.comments.domain.repositories.CommentsRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExperimentalSerializationApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
class GetCommentsUseCaseTest {

    @MockK
    private lateinit var commentsRepository: CommentsRepository

    private lateinit var useCase: GetCommentsUseCase

    @BeforeEach
    fun setUp() {
        useCase = GetCommentsUseCase(commentsRepository)
    }

    @Test
    internal fun `Should return CommentsState GettingCommentsSuccessfully when commentsRepository emits comments`(): Unit = runTest(UnconfinedTestDispatcher()) {
        // Given
        val commentsSuccessfullyState = CommentsState.GettingCommentsSuccessfully(listOf())
        every { commentsRepository.getComments() } returns flow {
            emit(commentsSuccessfullyState)
        }

        // When
        val flowTestObserver = useCase.execute(Unit).test(this)

        // Then
        flowTestObserver.assertValues(commentsSuccessfullyState).finish()
    }

    @Test
    internal fun `Should return CommentsState ErrorGettingComments when commentsRepository can not emit comments`(): Unit = runTest(UnconfinedTestDispatcher()) {
        // Given
        val commentsErrorState = CommentsState.ErrorGettingComments
        every { commentsRepository.getComments() } returns flow {
            emit(commentsErrorState)
        }

        // When
        val flowTestObserver = useCase.execute(Unit).test(this)

        // Then
        flowTestObserver.assertValues(commentsErrorState).finish()
    }
}