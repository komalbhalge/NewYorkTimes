package com.kb.nytimes.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.kb.nytimes.domain.usecase.SearchArticlesUseCase
import com.kb.nytimes.domain.usecase.base.BaseSearchArticlesUseCase
import com.kb.nytimes.data.model.Doc
import com.kb.nytimes.data.model.SearchedArticlesResponse
import com.kb.nytimes.util.Constants.ERROR_TAG
import com.kb.nytimes.util.extension.launchWith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchArticlesUseCase: SearchArticlesUseCase,
) : BaseViewModel() {

    val articlesResponse = MutableLiveData<SearchedArticlesResponse>()

    val articlesList = Transformations.map(articlesResponse, this::getArticleList)
    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext


    fun loadArticles(searchQuery: String) {
        retrieveMostEmailedList(searchQuery)
    }

    private fun retrieveMostEmailedList(searchQuery: String) {
        val articlesUseCase = searchArticlesUseCase
        articlesUseCase.build(
            BaseSearchArticlesUseCase.Params(searchQuery = searchQuery)
        ).onEach { articlesResponse.value = it }
            .catch {
                if (it is IOException) {
                    showNoInternetMessage()
                }
            }
            .launchWith(this, onError = { printError(it) })
    }

    fun printError(error: Throwable) {
        Log.e(ERROR_TAG, error.localizedMessage)
        shouldShowApiErrorAlert.value = error
    }

    fun getArticleList(response: SearchedArticlesResponse): List<Doc> {
        return response.response.docs
    }

}