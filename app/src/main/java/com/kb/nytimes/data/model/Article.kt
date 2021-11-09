package com.kb.nytimes.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
data class Article(
    @SerializedName("abstract")
    val abstract_tx: String? = null,
    val adx_keywords: String? = null,
    val asset_id: Long? = null,
    val byline: String? = null,
    val column: Any? = null,
    val des_facet: List<String>? = null,
    val eta_id: Int? = null,
    val geo_facet: List<String>? = null,
    val id: Long? = null,
    val media: List<Media>? = null,
    val nytdsection: String? = null,
    val org_facet: List<String>? = null,
    val per_facet: List<String>? = null,
    val published_date: String? = null,
    val section: String? = null,
    val source: String? = null,
    val subsection: String? = null,
    val title: String? = null,
    val type: String? = null,
    val updated: String? = null,
    val uri: String? = null,
    val url: String? = null,
): Serializable