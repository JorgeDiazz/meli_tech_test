package com.meli.items

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.ExperimentalPagingApi
import coil.load
import com.app.base.None
import com.app.base.interfaces.Logger
import com.meli.components.utils.viewBinding
import com.meli.items.databinding.FragmentItemDetailsBinding
import com.meli.items.domain.data.ItemsState
import com.meli.items.view.uimodel.AttributeUiModel
import com.meli.items.view.uimodel.ItemUiModel
import com.meli.items.view.uimodel.SellerUiModel
import com.meli.items.viewmodels.ItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Represents the detailed information of a given item.
 *
 */
@AndroidEntryPoint
@ExperimentalPagingApi
class ItemDetailsFragment : DialogFragment(R.layout.fragment_item_details) {

  @Inject
  lateinit var logger: Logger

  private val itemsViewModel by viewModels<ItemsViewModel>()
  private val binding by viewBinding(FragmentItemDetailsBinding::bind)

  private val itemUiModel: ItemUiModel by lazy {
    ItemDetailsFragmentArgs.fromBundle(requireArguments()).itemUiModel
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initializeView()
    initializeNewsSubscription()
  }

  private fun initializeView() {
    loadThumbnail(itemUiModel)
    setUpItemView(itemUiModel)
    setUpAttributesView(itemUiModel.attributes)
    setUpSellerView(itemUiModel.seller)
    initializeToolbar()
  }

  private fun loadThumbnail(itemUiModel: ItemUiModel) {
    binding.imageViewThumbnail.load(itemUiModel.thumbnail) {
      crossfade(true)
      placeholder(R.drawable.placeholder_image)
      error(R.drawable.error_image)
    }
  }

  private fun setUpItemView(itemUiModel: ItemUiModel) {
    binding.contentItemDetails.textViewItemTitle.text = itemUiModel.title
    binding.contentItemDetails.textViewItemPrice.text = itemUiModel.price
  }

  private fun setUpAttributesView(attributesUiModel: List<AttributeUiModel>) {
    val attributesList: List<String> = attributesUiModel.map { "${it.name}: ${it.valueName}" }
    val adapter: ArrayAdapter<String> =
      ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, attributesList)

    binding.contentAttributesDetails.listViewAttributes.adapter = adapter
  }

  private fun setUpSellerView(sellerUiModel: SellerUiModel) {
    binding.contentSellerDetails.textViewSellerName.text = sellerUiModel.nickname

    val addressShort = listOf(
      sellerUiModel.address
    )

    binding.contentSellerDetails.textViewFullSellerAddress.setTextMaxLength(10)

    binding.contentSellerDetails.textViewFullSellerAddress.setContent(
      addressShort.joinToString(",\n")
    )
  }

  private fun initializeToolbar() {
    binding.toolbarItemDetails.apply {
      (requireActivity() as AppCompatActivity).setSupportActionBar(this.toolbar)

      styleToolbarWithLightTheme()

      setTitle(getString(R.string.item_id, itemUiModel.id))

      setBackButton(true)
      setBackButtonListener { popFragment() }
    }
  }

  private fun popFragment() = requireActivity().onBackPressed()

  private fun initializeNewsSubscription() {
    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        itemsViewModel.newsSharedFlow.collectLatest { itemsStateNews ->
          handleNews(itemsStateNews)
        }
      }
    }
  }

  private fun handleNews(news: ItemsState) {
    when (news) {
      is ItemsState.ErrorState -> {
        Toast.makeText(requireContext(), news.errorMessage, Toast.LENGTH_SHORT).show()
        logger.e(news.errorMessage)
      }

      else -> None
    }
  }
}
