package com.meli.items.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchItemsResponse(
  @Json(name = "available_filters")
  val availableFilters: List<AvailableFilter>? = null,
  @Json(name = "available_sorts")
  val availableSorts: List<AvailableSort>? = null,
  @Json(name = "country_default_time_zone")
  val countryDefaultTimeZone: String? = null,
  @Json(name = "filters")
  val filters: List<Filter>? = null,
  @Json(name = "paging")
  val paging: Paging? = null,
  @Json(name = "pdp_tracking")
  val pdpTracking: PdpTracking? = null,
  @Json(name = "query")
  val query: String? = null,
  @Json(name = "ranking_introspection")
  val rankingIntrospection: RankingIntrospection? = null,
  @Json(name = "results")
  val resultXES: List<ResultX>? = null,
  @Json(name = "site_id")
  val siteId: String? = null,
  @Json(name = "sort")
  val sort: Sort? = null,
  @Json(name = "user_context")
  val userContext: Any? = null
)
