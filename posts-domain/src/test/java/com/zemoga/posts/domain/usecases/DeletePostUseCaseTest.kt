package com.zemoga.posts.domain.usecases

import androidx.paging.ExperimentalPagingApi
import com.app.base.test
import com.zemoga.posts.domain.repositories.PostsRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
class DeletePostUseCaseTest {

    @MockK
    private lateinit var postsRepository: PostsRepository

    private lateinit var useCase: DeletePostUseCase

    @BeforeEach
    fun setUp() {
        useCase = DeletePostUseCase(postsRepository)
    }

    @Test
    internal fun `Should return true when postsRepository can delete the post`(): Unit = runTest(UnconfinedTestDispatcher()) {
        // Given
        val postId = 123

        every { postsRepository.deletePost(postId) } returns flow {
            emit(true)
        }

        // When
        val flowTestObserver = useCase.execute(postId).test(this)

        // Then
        flowTestObserver.assertValues(true).finish()
    }

    @Test
    internal fun `Should return true when postsRepository can not delete the post`(): Unit = runTest(UnconfinedTestDispatcher()) {
        // Given
        val postId = 124

        every { postsRepository.deletePost(postId) } returns flow {
            emit(true)
        }

        // When
        val flowTestObserver = useCase.execute(postId).test(this)

        // Then
        flowTestObserver.assertValues(true).finish()
    }
}