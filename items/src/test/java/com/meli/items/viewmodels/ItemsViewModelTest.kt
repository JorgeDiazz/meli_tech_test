package com.meli.items.viewmodels

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.app.base.interfaces.FlowUseCase
import com.app.core.interfaces.AppResources
import com.meli.items.domain.data.Item
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class ItemsViewModelTest {

    private lateinit var viewModel: ItemsViewModel
    private lateinit var searchItemsUseCase: FlowUseCase<String, PagingData<Item>>
    private lateinit var appResources: AppResources
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        searchItemsUseCase = mockk(relaxed = true)
        appResources = mockk(relaxed = true)
        viewModel = ItemsViewModel(appResources, searchItemsUseCase)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun given_view_active_when_initialize_then_perform_default_search() = runTest {
        // GIVEN
        val expectedPagingData = PagingData.empty<Item>()
        coEvery { searchItemsUseCase.execute("") } returns flowOf(expectedPagingData)

        // WHEN
        viewModel.onViewActive()
        testDispatcher.scheduler.advanceUntilIdle()

        // THEN
        coVerify { searchItemsUseCase.execute("") }
    }

    @Test
    fun given_specific_query_when_search_then_use_that_query() = runTest {
        // GIVEN
        val searchQuery = "iphone"
        val expectedPagingData = PagingData.empty<Item>()
        coEvery { searchItemsUseCase.execute(searchQuery) } returns flowOf(expectedPagingData)

        // WHEN
        viewModel.searchItems(searchQuery)
        testDispatcher.scheduler.advanceUntilIdle()

        // THEN
        coVerify { searchItemsUseCase.execute(searchQuery) }
    }

    @Test
    fun given_empty_query_when_search_then_use_default_search() = runTest {
        // GIVEN
        val emptyQuery = ""
        val expectedPagingData = PagingData.empty<Item>()
        coEvery { searchItemsUseCase.execute(emptyQuery) } returns flowOf(expectedPagingData)

        // WHEN
        viewModel.searchItems(emptyQuery)
        testDispatcher.scheduler.advanceUntilIdle()

        // THEN
        coVerify { searchItemsUseCase.execute(emptyQuery) }
    }

    @Test
    fun given_use_case_returns_data_when_search_then_update_state_flow() = runTest {
        // GIVEN
        val query = "test"
        val item = createTestItem()
        val pagingData = PagingData.from(listOf(item))
        coEvery { searchItemsUseCase.execute(query) } returns flowOf(pagingData)

        // WHEN
        viewModel.searchItems(query)
        testDispatcher.scheduler.advanceUntilIdle()

        // THEN
        coVerify { searchItemsUseCase.execute(query) }
    }

    private fun createTestItem() = Item(
        id = 1,
        title = "Test Item",
        price = 100.0,
        thumbnail = "url",
        seller = mockk(relaxed = true),
        address = mockk(relaxed = true),
        attributes = emptyList()
    )
}
