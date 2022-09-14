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
class DeleteNonFavoritePostsUseCaseTest {

    @MockK
    private lateinit var postsRepository: PostsRepository

    private lateinit var useCase: DeleteNonFavoritePostsUseCase

    @BeforeEach
    fun setUp() {
        useCase = DeleteNonFavoritePostsUseCase(postsRepository)
    }

    @Test
    internal fun `Should return true when postsRepository can delete non favorite posts`(): Unit = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { postsRepository.deleteNonFavoritePosts() } returns flow {
            emit(true)
        }

        // When
        val flowTestObserver = useCase.execute(Unit).test(this)

        // Then
        flowTestObserver.assertValues(true).finish()
    }

    @Test
    internal fun `Should return false when postsRepository can not delete non favorite posts`(): Unit = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { postsRepository.deleteNonFavoritePosts() } returns flow {
            emit(false)
        }

        // When
        val flowTestObserver = useCase.execute(Unit).test(this)

        // Then
        flowTestObserver.assertValues(false).finish()
    }
}