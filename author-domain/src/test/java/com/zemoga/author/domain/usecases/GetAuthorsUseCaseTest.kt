package com.zemoga.author.domain.usecases


import com.app.base.test
import com.zemoga.author.domain.data.AuthorsState
import com.zemoga.author.domain.repositories.AuthorsRepository
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
class GetAuthorsUseCaseTest {

    @MockK
    private lateinit var authorsRepository: AuthorsRepository

    private lateinit var useCase: GetAuthorsUseCase

    @BeforeEach
    fun setUp() {
        useCase = GetAuthorsUseCase(authorsRepository)
    }

    @Test
    internal fun `Should return AuthorsState GettingAuthorsSuccessfully when authorsRepository emits authors`(): Unit = runTest(UnconfinedTestDispatcher()) {
        // Given
        val authorsSuccessfullyState = AuthorsState.GettingAuthorsSuccessfully(listOf())
        every { authorsRepository.getAuthors() } returns flow {
            emit(authorsSuccessfullyState)
        }

        // When
        val flowTestObserver = useCase.execute(Unit).test(this)

        // Then
        flowTestObserver.assertValues(authorsSuccessfullyState).finish()
    }

    @Test
    internal fun `Should return AuthorsState ErrorGettingAuthors when authorsRepository can not emit authors`(): Unit = runTest(UnconfinedTestDispatcher()) {
        // Given
        val authorsErrorState = AuthorsState.ErrorGettingAuthors
        every { authorsRepository.getAuthors() } returns flow {
            emit(authorsErrorState)
        }

        // When
        val flowTestObserver = useCase.execute(Unit).test(this)

        // Then
        flowTestObserver.assertValues(authorsErrorState).finish()
    }
}