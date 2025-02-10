package com.meli.items.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultX(
  @Json(name = "accepts_mercadopago")
  val acceptsMercadopago: Boolean?,
  @Json(name = "address")
  val address: Address?,
  @Json(name = "attributes")
  val attributes: List<Attribute>?,
  @Json(name = "available_quantity")
  val availableQuantity: Double?,
  @Json(name = "buying_mode")
  val buyingMode: String?,
  @Json(name = "catalog_listing")
  val catalogListing: Boolean?,
  @Json(name = "catalog_product_id")
  val catalogProductId: String?,
  @Json(name = "category_id")
  val categoryId: String?,
  @Json(name = "condition")
  val condition: String?,
  @Json(name = "currency_id")
  val currencyId: String?,
  @Json(name = "discounts")
  val discounts: Any?,
  @Json(name = "domain_id")
  val domainId: String?,
  @Json(name = "id")
  val id: String?,
  @Json(name = "installments")
  val installments: Installments?,
  @Json(name = "inventory_id")
  val inventoryId: Any?,
  @Json(name = "listing_type_id")
  val listingTypeId: String?,
  @Json(name = "official_store_id")
  val officialStoreId: Double?,
  @Json(name = "official_store_name")
  val officialStoreName: String?,
  @Json(name = "order_backend")
  val orderBackend: Double?,
  @Json(name = "original_price")
  val originalPrice: Any?,
  @Json(name = "permalink")
  val permalink: String?,
  @Json(name = "price")
  val price: Double?,
  @Json(name = "promotion_decorations")
  val promotionDecorations: Any?,
  @Json(name = "promotions")
  val promotions: Any?,
  @Json(name = "sale_price")
  val salePrice: SalePrice?,
  @Json(name = "sanitized_title")
  val sanitizedTitle: String?,
  @Json(name = "seller")
  val seller: Seller?,
  @Json(name = "shipping")
  val shipping: Shipping?,
  @Json(name = "site_id")
  val siteId: String?,
  @Json(name = "stop_time")
  val stopTime: String?,
  @Json(name = "thumbnail")
  val thumbnail: String?,
  @Json(name = "thumbnail_id")
  val thumbnailId: String?,
  @Json(name = "title")
  val title: String?,
  @Json(name = "use_thumbnail_id")
  val useThumbnailId: Boolean?,
  @Json(name = "variation_filters")
  val variationFilters: List<String>?,
  @Json(name = "variation_id")
  val variationId: String?,
  @Json(name = "variations_data")
  val variationsData: VariationsData?,
  @Json(name = "winner_item_id")
  val winnerItemId: Any?
)
