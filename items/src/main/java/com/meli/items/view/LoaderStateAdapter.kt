package com.meli.items.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.meli.items.R
import com.meli.items.databinding.CardItemLoaderBinding
import com.meli.items.view.LoaderStateAdapter.LoaderViewHolder

/**
 * Represents the loader state of items recycler view.
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
                val view = inflater.inflate(R.layout.card_item_loader, parent, false)
                return LoaderViewHolder(view, retry)
            }
        }

        private val binding = CardItemLoaderBinding.bind(view)

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
