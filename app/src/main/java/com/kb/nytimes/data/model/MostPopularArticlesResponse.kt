package com.kb.nytimes.data.model

import com.google.gson.annotations.SerializedName

data class MostPopularArticlesResponse(
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("num_results")
    val num_results: Int,
    @SerializedName("results")
    val results: List<Article>,
    @SerializedName("status")
    val status: String
)