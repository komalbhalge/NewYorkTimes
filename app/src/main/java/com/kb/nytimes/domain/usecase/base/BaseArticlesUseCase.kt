package com.kb.nytimes.domain.usecase.base

import com.kb.nytimes.data.model.MostPopularArticlesResponse

abstract class BaseArticlesUseCase : BaseUseCase<BaseArticlesUseCase.Params, MostPopularArticlesResponse>() {
    data class Params(val period: Int)
}