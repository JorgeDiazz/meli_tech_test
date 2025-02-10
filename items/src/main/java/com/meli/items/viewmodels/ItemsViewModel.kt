package com.meli.items.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.app.base.interfaces.FlowUseCase
import com.app.core.interfaces.AppResources
import com.meli.items.domain.data.Item
import com.meli.items.domain.data.ItemsState
import com.meli.items.qualifiers.GetItems
import com.meli.items.view.uimodel.AttributeUiModel
import com.meli.items.view.uimodel.ItemUiModel
import com.meli.items.view.uimodel.SellerUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Represents the ViewModel layer of ItemsFragment & ItemDetailsFragment.
 *
 */
@HiltViewModel
@ExperimentalPagingApi
class ItemsViewModel @Inject constructor(
  private val resources: AppResources,
  @GetItems private val searchItemsUseCase: FlowUseCase<String, PagingData<Item>>,
) : ViewModel() {

  private val _itemsPagingStateFlow = MutableStateFlow<PagingData<ItemUiModel>>(PagingData.empty())
  val itemsPagingStateFlow: StateFlow<PagingData<ItemUiModel>> = _itemsPagingStateFlow

  private val _newsSharedFlow = MutableSharedFlow<ItemsState>()
  val newsSharedFlow: SharedFlow<ItemsState> = _newsSharedFlow

  fun onViewActive() {
    searchItems()
  }

  fun searchItems(query: String = "") = viewModelScope.launch {
    searchItemsUseCase.execute(query).cachedIn(viewModelScope).distinctUntilChanged()
      .flowOn(Dispatchers.IO).collectLatest {
        _itemsPagingStateFlow.value = it.toUiModel()
      }
  }
}

private fun PagingData<Item>.toUiModel(): PagingData<ItemUiModel> = map { item ->
  item.toUiModel()
}

private fun Item.toUiModel(): ItemUiModel = ItemUiModel(
  id = id,
  title = title,
  thumbnail = thumbnail,
  price = price.toString(),
  seller = SellerUiModel(
    nickname = seller.nickname,
    address = "${address.cityName}, ${address.stateName}"
  ),
  attributes = attributes.map { attr ->
    AttributeUiModel(
      name = attr.name,
      valueName = attr.valueName
    )
  }
)
