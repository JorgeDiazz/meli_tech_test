package com.zemoga.posts.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zemoga.posts.R
import com.zemoga.posts.databinding.CardItemPostLoaderBinding
import com.zemoga.posts.view.LoaderStateAdapter.LoaderViewHolder

/**
 * Represents the loader state of posts recycler view.
 *
 */
class LoaderStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoaderViewHolder>() {

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder.getInstance(parent, retry)
    }

    class LoaderViewHolder(view: View, retry: () -> Unit) : RecyclerView.ViewHolder(view) {
        companion object {
            fun getInstance(parent: ViewGroup, retry: () -> Unit): LoaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.card_item_post_loader, parent, false)
                return LoaderViewHolder(view, retry)
            }
        }

        private val binding = CardItemPostLoaderBinding.bind(view)

        init {
            binding.buttonRetry.setOnClickListener {
                retry()
            }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Loading) {
                binding.motionLayoutLoader.transitionToEnd()
            } else {
                binding.motionLayoutLoader.transitionToStart()
            }
        }
    }
}