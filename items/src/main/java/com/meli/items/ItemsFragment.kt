package com.meli.items

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.base.None
import com.app.base.interfaces.Cache
import com.app.base.interfaces.Logger
import com.app.base.others.READ_ITEMS_FROM_REMOTE_KEY
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.meli.components.utils.viewBinding
import com.meli.items.databinding.FragmentItemsBinding
import com.meli.items.domain.data.ItemsState
import com.meli.items.view.ItemsAdapter
import com.meli.items.view.LoaderStateAdapter
import com.meli.items.view.uimodel.ItemUiModel
import com.meli.items.viewmodels.ItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Represents the list of items fetched in from the API.
 *
 */
@AndroidEntryPoint
@ExperimentalPagingApi
class ItemsFragment : Fragment(R.layout.fragment_items), ItemsAdapter.OnClickListener {

  @Inject
  lateinit var logger: Logger

  @Inject
  lateinit var cache: Cache

  private val viewModel by viewModels<ItemsViewModel>()
  private val binding by viewBinding(FragmentItemsBinding::bind)

  private lateinit var itemsRecyclerView: RecyclerView
  private lateinit var itemsAdapter: ItemsAdapter
  private lateinit var swipeRefreshLayout: SwipeRefreshLayout
  private lateinit var searchEditText: TextInputEditText
  private lateinit var searchButton: Button
  private lateinit var progressBar: ProgressBar

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initializeView()
    initializeViewModel()
    initializeObserver()
    initializeNewsSubscription()
  }

  private fun initializeView() {
    setUpRecyclerView()
    setUpSearchView()
    setUpLoadingState()
  }

  private fun setUpRecyclerView() {
    itemsAdapter = ItemsAdapter(resources, this).apply {
      addLoadStateListener { loadState ->
        val mediatorLoadState: LoadState? = loadState.mediator?.refresh

        if (mediatorLoadState is LoadState.NotLoading) {
          swipeRefreshLayout.isRefreshing = false
        } else if (mediatorLoadState is LoadState.Error) {
          Toast.makeText(
            requireContext(),
            getString(R.string.loading_resources_from_local_database),
            Toast.LENGTH_SHORT
          ).show()
          swipeRefreshLayout.isRefreshing = false
        }

      }
    }

    itemsRecyclerView = binding.recyclerView
    itemsRecyclerView.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = itemsAdapter.withLoadStateFooter(LoaderStateAdapter { itemsAdapter.retry() })
      addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    swipeRefreshLayout = binding.swipeRefreshLayout

    swipeRefreshLayout.setOnRefreshListener {
      cache.saveBoolean(READ_ITEMS_FROM_REMOTE_KEY, true)

      itemsAdapter.refresh()
    }

  }

  private fun setUpSearchView() {
    searchEditText = binding.searchEditText
    searchButton = binding.searchButton

    searchButton.setOnClickListener {
      performSearch()
    }

    searchEditText.setOnEditorActionListener { _, actionId, _ ->
      if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        performSearch()
        true
      } else {
        false
      }
    }
  }

  private fun setUpLoadingState() {
    progressBar = binding.progressBarScreenLoader

    itemsAdapter.addLoadStateListener { loadState ->
      val isLoading = loadState.refresh is LoadState.Loading
      val isError = loadState.refresh is LoadState.Error

      progressBar.isVisible = isLoading

      if (isError) {
        Toast.makeText(
          requireContext(),
          getString(R.string.loading_resources_from_local_database),
          Toast.LENGTH_SHORT
        ).show()
      }

      val isEmpty =
        !isLoading && loadState.append.endOfPaginationReached && itemsAdapter.itemCount == 0
      if (isEmpty) {
        showEmptyStateToast()
      }
    }
  }

  private fun showEmptyStateToast() {
    Toast.makeText(
      requireContext(),
      getString(R.string.no_results_found),
      Toast.LENGTH_SHORT
    ).show()
  }

  private fun performSearch() {
    val query = searchEditText.text?.toString()?.trim() ?: ""
    if (query.isNotEmpty()) {
      cache.saveBoolean(READ_ITEMS_FROM_REMOTE_KEY, true)
      progressBar.isVisible = true
      viewModel.searchItems(query)
    }
  }

  private fun initializeViewModel() {
    viewModel.onViewActive()
  }

  private fun initializeObserver() {
    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.itemsPagingStateFlow.collect { itemsPagingData ->
          observeItemsPagingData(itemsPagingData)
        }
      }
    }
  }

  private fun observeItemsPagingData(itemsPagingData: PagingData<ItemUiModel>) {
    lifecycleScope.launchWhenStarted {
      itemsAdapter.submitData(itemsPagingData)
    }
  }

  private fun initializeNewsSubscription() {
    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.newsSharedFlow.collectLatest { itemsStateNews ->
          handleNews(itemsStateNews)
        }
      }
    }
  }

  private fun handleNews(news: ItemsState) {
    when (news) {
      is ItemsState.ErrorState -> {
        Snackbar.make(requireView(), news.errorMessage, Snackbar.LENGTH_SHORT).show()
        logger.e(news.errorMessage)
      }

      else -> None
    }
  }

  override fun onItemClick(itemUiModel: ItemUiModel) {
    navigateToItemDetailsFragment(itemUiModel)
  }

  private fun navigateToItemDetailsFragment(itemUiModel: ItemUiModel) {
    val action = ItemsFragmentDirections.actionItemsFragmentToItemDetailsFragment(itemUiModel)
    findNavController().navigate(action)
  }
}
