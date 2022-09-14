package com.zemoga.posts.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.app.base.interfaces.FlowUseCase
import com.app.core.Event
import com.zemoga.author.domain.data.Author
import com.zemoga.author.domain.data.AuthorsState
import com.zemoga.author.qualifiers.GetAuthors
import com.zemoga.authors.view.uimodel.AddressUiModel
import com.zemoga.authors.view.uimodel.AuthorUiModel
import com.zemoga.authors.view.uimodel.CompanyUiModel
import com.zemoga.authors.view.uimodel.GeolocationUiModel
import com.zemoga.comments.domain.data.Comment
import com.zemoga.comments.domain.data.CommentsState
import com.zemoga.comments.qualifiers.GetComments
import com.zemoga.comments.view.uimodel.CommentUiModel
import com.zemoga.posts.domain.data.Post
import com.zemoga.posts.domain.data.PostsState
import com.zemoga.posts.qualifiers.DeleteNonFavoritePosts
import com.zemoga.posts.qualifiers.DeletePost
import com.zemoga.posts.qualifiers.GetPosts
import com.zemoga.posts.qualifiers.UpdateFavoritePost
import com.zemoga.posts.view.uimodel.PostUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Represents the ViewModel layer of PostFragment & PostDetailsFragment.
 *
 */
@HiltViewModel
@ExperimentalPagingApi
class PostsViewModel @Inject constructor(
    @GetPosts private val getPostsUseCase: FlowUseCase<Unit, PagingData<Post>>,
    @DeletePost private val deletePostUseCase: FlowUseCase<Int, Boolean>,
    @DeleteNonFavoritePosts private val deleteNonFavoritePostsUseCase: FlowUseCase<Unit, Boolean>,
    @UpdateFavoritePost private val updateFavoritePostUseCase: FlowUseCase<Pair<Int, Boolean>, Boolean>,
    @GetAuthors private val getAuthorsUseCase: FlowUseCase<Unit, AuthorsState>,
    @GetComments private val getCommentsUseCase: FlowUseCase<Unit, CommentsState>,
) : ViewModel() {

    private val _liveData: MutableLiveData<PagingData<PostUiModel>> = MutableLiveData()
    val liveData: LiveData<PagingData<PostUiModel>> = _liveData

    private val _news = MutableLiveData<Event<PostsState>>()
    val news: LiveData<Event<PostsState>> = _news

    fun onViewActive() {
        loadPosts()
    }

    fun deletePost(postId: Int) = viewModelScope.launch {
        deletePostUseCase.execute(postId)
            .collect { deleted ->
                if (deleted) {
                    _news.value = Event(PostsState.PostDeletedSuccessfully(postId))
                } else {
                    _news.value = Event(PostsState.ErrorDeletingPost)
                }
            }
    }

    fun deleteNonFavoritePosts() = viewModelScope.launch {
        deleteNonFavoritePostsUseCase.execute(Unit)
            .collect { deleted ->
                if (deleted) {
                    _news.value = Event(PostsState.NonFavoritePostsDeletedSuccessfully)
                } else {
                    _news.value = Event(PostsState.ErrorDeletingNonFavoritePosts)
                }
            }
    }


    fun updateFavoritePost(postId: Int, favorite: Boolean) = viewModelScope.launch {
        updateFavoritePostUseCase.execute(postId to favorite)
            .collect { updated ->
                if (updated) {
                    _news.value = Event(PostsState.PostUpdatedSuccessfully(postId))
                } else {
                    _news.value = Event(PostsState.ErrorUpdatingPost)
                }
            }
    }


    private fun loadPosts() = viewModelScope.launch {
        combine(
            getPostsUseCase.execute(Unit).cachedIn(viewModelScope).distinctUntilChanged(),
            getAuthorsUseCase.execute(Unit),
            getCommentsUseCase.execute(Unit)
        ) { postPagingData: PagingData<Post>, authorsState: AuthorsState, commentsState: CommentsState ->
            Triple(postPagingData, authorsState, commentsState)
        }
            .collectLatest {
                val (postPagingData, authorsState, commentsState) = it

                if (authorsState is AuthorsState.GettingAuthorsSuccessfully && commentsState is CommentsState.GettingCommentsSuccessfully) {
                    val authors = authorsState.authorsList
                    val comments = commentsState.commentsList

                    _liveData.value = postPagingData.map { post ->
                        post.toUiModel(
                            author = authors.first { author -> author.id == post.userId }.toUiModel(),
                            comments = comments.filter { comment -> comment.postId == post.id }.toUiModel()
                        )
                    }
                } else {
                    _news.value = Event(PostsState.ErrorUpdatingPost)
                }
            }
    }

    private fun Post.toUiModel(author: AuthorUiModel, comments: List<CommentUiModel>): PostUiModel =
        PostUiModel(
            id, userId, title, body, menuVisible = false, favorite = favorite,
            deleted = false, authorUiModel = author, commentsUiModel = comments,
        )

    private fun Author.toUiModel(): AuthorUiModel = AuthorUiModel(
        id, name, username, email,
        AddressUiModel(
            address.street,
            address.suite,
            address.city,
            address.zipcode,
            GeolocationUiModel(address.geolocation.latitude, address.geolocation.longitude),
        ),
        phone,
        website,
        CompanyUiModel(company.name, company.catchPhrase, company.bs),
    )

    private fun List<Comment>.toUiModel(): List<CommentUiModel> = map { it.toUiModel() }
    private fun Comment.toUiModel(): CommentUiModel = CommentUiModel(postId, id, name, email, body)

}

