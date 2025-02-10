package com.meli.items.domain.data

sealed class ItemsState {
      data class ErrorState(val errorMessage: String) : ItemsState()

}
