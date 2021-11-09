package com.kb.nytimes.domain.usecase

import com.kb.nytimes.data.model.MostPopularArticlesResponse
import com.kb.nytimes.domain.repository.ArticlesRepository
import com.kb.nytimes.domain.usecase.base.BaseArticlesUseCase
import com.kb.nytimes.util.extension.flowSingle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMostViewedArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticlesRepository
)  : BaseArticlesUseCase() {
    override fun onBuild(params: Params): Flow<MostPopularArticlesResponse> {
        return flowSingle {
            articlesRepository.getMostViewedArticles(
                params.period
            )
        }
    }
}