package com.zemoga.author.domain.data

sealed class AuthorsState {
  data class GettingAuthorsSuccessfully(val authorsList: List<Author>) : AuthorsState()
  object ErrorGettingAuthors : AuthorsState()
}
