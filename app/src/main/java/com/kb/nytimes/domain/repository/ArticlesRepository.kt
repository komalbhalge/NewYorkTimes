package com.kb.nytimes.domain.repository

import com.kb.nytimes.data.model.MostPopularArticlesResponse

interface ArticlesRepository {
    suspend fun getMostViewedArticles(period: Int): MostPopularArticlesResponse

    suspend fun getMostSharedArticles(period: Int): MostPopularArticlesResponse

    suspend fun getMostEmailedArticles(period: Int): MostPopularArticlesResponse
}