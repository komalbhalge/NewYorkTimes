package com.kb.nytimes.data.model

data class SearchedArticlesResponse(
    val copyright: String,
    val response: Response,
    val status: String
)