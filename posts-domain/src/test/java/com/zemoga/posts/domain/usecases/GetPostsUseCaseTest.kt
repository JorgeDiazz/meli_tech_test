package com.zemoga.posts.domain.usecases

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.app.base.test
import com.zemoga.posts.domain.data.Post
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
class GetPostsUseCaseTest {

    @MockK(relaxed = true)
    private lateinit var postsRepository: PostsRepository

    private lateinit var useCase: GetPostsUseCase

    @BeforeEach
    fun setUp() {
        useCase = GetPostsUseCase(postsRepository)

    }

    @Test
    internal fun `Should return no value when postsRepository getPosts returns an empty list`(): Unit = runTest(UnconfinedTestDispatcher()) {
        // Given
        val pagingData = PagingData.from(listOf<Post>())

        every { postsRepository.getPosts() } returns flow {
            emit(pagingData)
        }

        // When
        val flowTestObserver = useCase.execute(Unit).test(this)

        // Then
        flowTestObserver.assertNoValues().finish()
    }
}