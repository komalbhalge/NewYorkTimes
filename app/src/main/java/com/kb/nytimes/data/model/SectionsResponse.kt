package com.kb.nytimes.data.model

import com.google.gson.annotations.SerializedName

data class SectionsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("copyright") val copyright: String,
    @SerializedName("num_results") val num_results: Int,
    @SerializedName("results") val results: List<SectionDetail>
)

data class SectionDetail(
    @SerializedName("section") val section: String,
    @SerializedName("display_name") val display_name: String
)