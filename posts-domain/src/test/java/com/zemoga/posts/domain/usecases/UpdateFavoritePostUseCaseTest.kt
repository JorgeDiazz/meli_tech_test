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
class UpdateFavoritePostUseCaseTest {

    @MockK
    private lateinit var postsRepository: PostsRepository

    private lateinit var useCase: UpdateFavoritePostUseCase

    @BeforeEach
    fun setUp() {
        useCase = UpdateFavoritePostUseCase(postsRepository)
    }

    @Test
    internal fun `Should return true when postsRepository can update the post`(): Unit = runTest(UnconfinedTestDispatcher()) {
        // Given
        val postId = 123
        val favorite = true

        every { postsRepository.updateFavoritePost(postId, favorite) } returns flow {
            emit(true)
        }

        // When
        val flowTestObserver = useCase.execute(postId to favorite).test(this)

        // Then
        flowTestObserver.assertValues(true).finish()
    }

    @Test
    internal fun `Should return true when postsRepository can not update the post`(): Unit = runTest(UnconfinedTestDispatcher()) {
        // Given
        val postId = 123
        val favorite = false

        every { postsRepository.updateFavoritePost(postId, favorite) } returns flow {
            emit(false)
        }

        // When
        val flowTestObserver = useCase.execute(postId to favorite).test(this)

        // Then
        flowTestObserver.assertValues(false).finish()
    }
}