package com.meli.items.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SalePrice(
    @Json(name = "amount")
    val amount: Double?,
    @Json(name = "conditions")
    val conditions: Conditions?,
    @Json(name = "currency_id")
    val currencyId: String?,
    @Json(name = "exchange_rate")
    val exchangeRate: Any?,
    @Json(name = "metadata")
    val metadata: MetadataX?,
    @Json(name = "payment_method_prices")
    val paymentMethodPrices: List<Any?>?,
    @Json(name = "payment_method_type")
    val paymentMethodType: String?,
    @Json(name = "price_id")
    val priceId: String?,
    @Json(name = "regular_amount")
    val regularAmount: Any?,
    @Json(name = "type")
    val type: String?
)
