package com.meli.items.domain.data

sealed class ItemsState {
      object EmptyState : ItemsState()
      data class ErrorState(val errorMessage: String) : ItemsState()


}
