package com.meli.items.domain.usecases

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.meli.items.domain.data.Item
import com.meli.items.domain.repositories.IItemsRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalPagingApi
class SearchItemsUseCaseTest {

    private lateinit var itemsRepository: IItemsRepository
    private lateinit var searchItemsUseCase: SearchItemsUseCase

    @BeforeEach
    fun setup() {
        itemsRepository = mockk(relaxed = true)
        searchItemsUseCase = SearchItemsUseCase(itemsRepository)
    }

    @Test
    fun givenEmptyQuery_whenExecute_thenUseDefaultSearchTerm() = runTest {
        // GIVEN
        val emptyQuery = ""
        val defaultSearch = "celular"
        val expectedPagingData = PagingData.empty<Item>()
        coEvery {
            itemsRepository.searchItems(
                query = defaultSearch,
                pagingConfig = any()
            )
        } returns flowOf(expectedPagingData)

        // WHEN
        val result = searchItemsUseCase.execute(emptyQuery)

        // THEN
        verify {
            itemsRepository.searchItems(
                query = defaultSearch,
                pagingConfig = any()
            )
        }
    }

    @Test
    fun givenSpecificQuery_whenExecute_thenUseProvidedQuery() = runTest {
        // GIVEN
        val specificQuery = "iphone"
        val expectedPagingData = PagingData.empty<Item>()
        coEvery {
            itemsRepository.searchItems(
                query = specificQuery,
                pagingConfig = any()
            )
        } returns flowOf(expectedPagingData)

        // WHEN
        searchItemsUseCase.execute(specificQuery)

        // THEN
        verify {
            itemsRepository.searchItems(
                query = specificQuery,
                pagingConfig = any()
            )
        }
    }

    @Test
    fun givenQueryWithSpaces_whenExecute_thenUseTrimmedQuery() = runTest {
        // GIVEN
        val queryWithSpaces = "  samsung s23  "
        val trimmedQuery = "samsung s23"
        val expectedPagingData = PagingData.empty<Item>()

        coEvery {
            itemsRepository.searchItems(
                query = trimmedQuery,
                pagingConfig = any()
            )
        } returns flowOf(expectedPagingData)

        // WHEN
        val result = searchItemsUseCase.execute(queryWithSpaces)

        // THEN
        verify {
            itemsRepository.searchItems(
                query = trimmedQuery,
                pagingConfig = any()
            )
        }
    }

    @Test
    fun givenRepositoryReturnsData_whenExecute_thenReturnSameFlow() = runTest {
        // GIVEN
        val query = "test"
        val expectedPagingData = PagingData.empty<Item>()
        val expectedFlow = flowOf(expectedPagingData)
        coEvery {
            itemsRepository.searchItems(
                query = query,
                pagingConfig = any()
            )
        } returns expectedFlow

        // WHEN
        val result = searchItemsUseCase.execute(query)

        // THEN
        assert(result == expectedFlow)
    }
}
