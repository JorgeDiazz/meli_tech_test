package com.meli.items.view

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.meli.items.R
import com.meli.items.databinding.CardItemBinding
import com.meli.items.view.uimodel.ItemUiModel

/**
 * Represents the adapter of items recycler view.
 *
 */
class ItemsAdapter(private val resources: Resources, private val onClickListener: OnClickListener) :
  PagingDataAdapter<ItemUiModel, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

  companion object {
    private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<ItemUiModel>() {
      override fun areItemsTheSame(oldItem: ItemUiModel, newItem: ItemUiModel): Boolean =
        oldItem == newItem

      override fun areContentsTheSame(oldItem: ItemUiModel, newItem: ItemUiModel): Boolean =
        oldItem == newItem
    }
  }

  interface OnClickListener {
    fun onItemClick(itemUiModel: ItemUiModel)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (getItemViewType(position)) {
      ItemViewType.NORMAL_ITEM_TYPE -> (holder as ItemViewHolder).bind(getItem(position))

      else -> throw UnsupportedOperationException()
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      ItemViewType.NORMAL_ITEM_TYPE -> ItemViewHolder.getInstance(parent, onClickListener)
      else -> throw UnsupportedOperationException()
    }
  }

  class ItemViewHolder(view: View, private val onClickListener: OnClickListener) :
    RecyclerView.ViewHolder(view) {

    companion object {
      fun getInstance(parent: ViewGroup, onClickListener: OnClickListener): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.card_item, parent, false)
        return ItemViewHolder(view, onClickListener)
      }
    }

    private val binding = CardItemBinding.bind(view)

    fun bind(itemUiModel: ItemUiModel?) {
      itemUiModel?.let {
        binding.imageViewThumbnail.load(itemUiModel.thumbnail) {
          crossfade(true)
          placeholder(R.drawable.placeholder_image)
          error(R.drawable.error_image)
        }

        binding.textViewTitle.text = itemUiModel.title

        binding.root.setOnClickListener {
          onClickListener.onItemClick(itemUiModel)
        }
      }
    }
  }

  class ItemViewType {
    companion object {
      const val NORMAL_ITEM_TYPE = 0
    }
  }
}
