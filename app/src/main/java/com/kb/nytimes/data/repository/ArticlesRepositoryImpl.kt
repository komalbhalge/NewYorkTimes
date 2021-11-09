package com.kb.nytimes.data.repository

import com.kb.nytimes.data.model.MostPopularArticlesResponse
import com.kb.nytimes.data.remote.ArticlesApi
import com.kb.nytimes.domain.repository.ArticlesRepository
import java.time.chrono.ChronoPeriod
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticlesRepositoryImpl @Inject constructor(
    private val api: ArticlesApi
) : ArticlesRepository {
    override suspend fun getMostViewedArticles(period: Int): MostPopularArticlesResponse {
        return api.mostViewedArticles(period)
    }

    override suspend fun getMostSharedArticles(period: Int): MostPopularArticlesResponse {
         return api.mostSharedArticles(period)
    }

    override suspend fun getMostEmailedArticles(period: Int): MostPopularArticlesResponse {
        return api.mostEmailedArticles(period)
    }
}