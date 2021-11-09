package com.kb.nytimes.domain.usecase

import com.kb.nytimes.data.model.SearchedArticlesResponse
import com.kb.nytimes.data.remote.ArticlesApi
import com.kb.nytimes.domain.usecase.base.BaseSearchArticlesUseCase
import com.kb.nytimes.domain.usecase.base.BaseUseCase
import com.kb.nytimes.util.extension.flowSingle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchArticlesUseCase @Inject constructor(private val articlesApi: ArticlesApi) :
    BaseUseCase<BaseSearchArticlesUseCase.Params, SearchedArticlesResponse>() {
    data class Params(val searchQuery: String, val source: String)

    override fun onBuild(params: BaseSearchArticlesUseCase.Params): Flow<SearchedArticlesResponse> {
        return flowSingle {
            articlesApi.searchArticles(
                searchResult = params.searchQuery
            )
        }
    }
}