package com.zemoga.posts.view

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zemoga.components.utils.addOnClickListener
import com.zemoga.posts.R
import com.zemoga.posts.databinding.CardItemPostBinding
import com.zemoga.posts.databinding.CardItemPostHiddenMenuBinding
import com.zemoga.posts.view.uimodel.PostUiModel

/**
 * Represents the adapter of posts recycler view.
 *
 */
class PostsAdapter(private val resources: Resources, private val onClickListener: OnClickListener) :
    PagingDataAdapter<PostUiModel, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<PostUiModel>() {
            override fun areItemsTheSame(oldItem: PostUiModel, newItem: PostUiModel): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: PostUiModel, newItem: PostUiModel): Boolean =
                oldItem == newItem
        }
    }

    interface OnClickListener {
        fun onPostClick(postUiModel: PostUiModel)

        fun onToggleFavoriteClick(postUiModel: PostUiModel, favorite: Boolean)

        fun onDeleteClick(postUiModel: PostUiModel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            PostViewType.NORMAL_POST_TYPE -> (holder as PostViewHolder).bind(getItem(position))
            PostViewType.MENU_POST_TYPE -> (holder as MenuPostViewHolder).bind(getItem(position))
            PostViewType.DELETED_POST_TYPE -> (holder as DeletedPostViewHolder).bind()

            else -> throw UnsupportedOperationException()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PostViewType.NORMAL_POST_TYPE -> PostViewHolder.getInstance(parent, onClickListener)
            PostViewType.MENU_POST_TYPE -> MenuPostViewHolder.getInstance(parent, resources, onClickListener)
            PostViewType.DELETED_POST_TYPE -> DeletedPostViewHolder.getInstance(parent)
            else -> throw UnsupportedOperationException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val post = getItem(position)
        return if (post?.deleted == true) {
            PostViewType.DELETED_POST_TYPE
        } else if (post?.menuVisible == true) {
            PostViewType.MENU_POST_TYPE
        } else {
            PostViewType.NORMAL_POST_TYPE
        }
    }

    fun showMenuAt(position: Int) {
        if (position > -1 && position < itemCount) {
            getItem(position)?.menuVisible = true
            notifyItemChanged(position)
        }
    }

    fun closeMenuAt(position: Int) {
        if (position > -1 && position < itemCount) {
            val item = getItem(position)
            if (item?.menuVisible == true) {
                getItem(position)?.menuVisible = false
                notifyItemChanged(position)
            }
        }
    }

    fun deletePost(postId: Int) {
        for (i in 0 until itemCount) {
            val item = getItem(i)

            if (item?.id == postId) {
                item.deleted = true
                notifyItemChanged(i)

                break
            }
        }
    }

    class PostViewHolder(view: View, private val onClickListener: OnClickListener) : RecyclerView.ViewHolder(view) {

        companion object {
            fun getInstance(parent: ViewGroup, onClickListener: OnClickListener): PostViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.card_item_post, parent, false)
                return PostViewHolder(view, onClickListener)
            }
        }

        private val binding = CardItemPostBinding.bind(view)

        fun bind(postUiModel: PostUiModel?) {
            postUiModel?.let {
                binding.textViewTitle.text = postUiModel.title
                binding.imageViewFavorite.isGone = !postUiModel.favorite

                binding.root.setOnClickListener {
                    onClickListener.onPostClick(postUiModel)
                }
            }
        }
    }

    class MenuPostViewHolder(view: View, private val resources: Resources, private val onClickListener: OnClickListener) : RecyclerView.ViewHolder(view) {

        companion object {
            fun getInstance(parent: ViewGroup, resources: Resources, onClickListener: OnClickListener): MenuPostViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.card_item_post_hidden_menu, parent, false)
                return MenuPostViewHolder(view, resources, onClickListener)
            }
        }

        private val binding = CardItemPostHiddenMenuBinding.bind(view)

        fun bind(postUiModel: PostUiModel?) {
            postUiModel?.let {
                binding.toggleFavorite.isChecked = postUiModel.favorite

                val favoriteLabelStringResource = if (postUiModel.favorite) R.string.unmark_as_favorite else R.string.mark_as_favorite
                binding.textViewFavorite.text = resources.getString(favoriteLabelStringResource)

                setUpOnFavoriteGroupClickListener(postUiModel)
                setUpOnDeleteGroupClickListener(postUiModel)
            }
        }


        private fun setUpOnFavoriteGroupClickListener(postUiModel: PostUiModel) {
            binding.toggleFavorite.setOnClickListener {
                val favorite = binding.toggleFavorite.isChecked
                val favoriteLabelStringResource = if (favorite) R.string.unmark_as_favorite else R.string.mark_as_favorite

                binding.textViewFavorite.text = resources.getString(favoriteLabelStringResource)

                onClickListener.onToggleFavoriteClick(postUiModel, favorite)
            }

            binding.textViewFavorite.setOnClickListener {
                binding.toggleFavorite.performClick()
            }
        }

        private fun setUpOnDeleteGroupClickListener(postUiModel: PostUiModel) {
            binding.groupDelete.addOnClickListener {
                onClickListener.onDeleteClick(postUiModel)
            }
        }
    }

    class DeletedPostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            fun getInstance(parent: ViewGroup): DeletedPostViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.card_item_post, parent, false)
                return DeletedPostViewHolder(view)
            }
        }

        private val binding = CardItemPostBinding.bind(view)

        fun bind() {
            binding.root.isGone = true
            binding.root.layoutParams = RecyclerView.LayoutParams(0, 0)
        }
    }

    class PostViewType {
        companion object {
            const val NORMAL_POST_TYPE = 0
            const val MENU_POST_TYPE = 1
            const val DELETED_POST_TYPE = 2
        }
    }
}
