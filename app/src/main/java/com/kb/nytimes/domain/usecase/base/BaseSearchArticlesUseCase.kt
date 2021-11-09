package com.kb.nytimes.domain.usecase.base

import com.kb.nytimes.data.model.SearchedArticlesResponse

abstract class BaseSearchArticlesUseCase : BaseUseCase<BaseSearchArticlesUseCase.Params, SearchedArticlesResponse>() {
    data class Params(val searchQuery: String)
}