package com.kb.nytimes.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kb.nytimes.R
import com.kb.nytimes.domain.usecase.FetchMostEmailedArticlesUseCase
import com.kb.nytimes.domain.usecase.FetchMostSharedArticlesUseCase
import com.kb.nytimes.domain.usecase.FetchMostViewedArticlesUseCase
import com.kb.nytimes.domain.usecase.base.BaseArticlesUseCase
import com.kb.nytimes.data.model.MostPopularArticlesResponse
import com.kb.nytimes.util.Constants.EMAILED
import com.kb.nytimes.util.Constants.SHARED
import com.kb.nytimes.util.Constants.VIEWED
import com.kb.nytimes.util.extension.launchWith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val fetchMostEmailedArticlesUseCase: FetchMostEmailedArticlesUseCase,
    private val fetchMostSharedArticlesUseCase: FetchMostSharedArticlesUseCase,
    private val fetchMostViewedArticlesUseCase: FetchMostViewedArticlesUseCase,
) : BaseViewModel() {

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext

    override fun showNoInternetMessage() {
        shouldShowNoInternetAlert.value = true
    }

    val articlesList = MutableLiveData<MostPopularArticlesResponse>()
    val toolbarLabel = MutableLiveData<Int>()

    fun loadArticles(type: String, period: Int) {
        when (type) {
            EMAILED -> retrieveMostEmailedList(period)
            SHARED -> retrieveMostSharedList(period)
            VIEWED -> retrieveMostViewedList(period)
        }

    }

    private fun retrieveMostEmailedList(period: Int) {
        toolbarLabel.value = R.string.emailed_articles

        val articlesUseCase = fetchMostEmailedArticlesUseCase
        articlesUseCase.build(
            BaseArticlesUseCase.Params(period)
        ).onEach { articlesList.postValue(it) }
            .catch {
                if (it is IOException) {
                    showNoInternetMessage()
                }
            }
            .launchWith(this, onError = { printError(it) })
    }


    private fun retrieveMostSharedList(period: Int) {
        toolbarLabel.value = R.string.shared_articles

        val articlesUseCase = fetchMostSharedArticlesUseCase
        articlesUseCase.build(
            BaseArticlesUseCase.Params(period)
        ).onEach { articlesList.postValue(it) }
            .catch {
                if (it is IOException) {
                    showNoInternetMessage()
                }
            }
            .launchWith(this, onError = { printError(it) })
    }

    private fun retrieveMostViewedList(period: Int) {
        toolbarLabel.value = R.string.viewed_articles

        val articlesUseCase = fetchMostViewedArticlesUseCase
        articlesUseCase.build(
            BaseArticlesUseCase.Params(period)
        ).onEach { articlesList.postValue(it) }
            .catch {
                if (it is IOException) {
                    showNoInternetMessage()
                }
            }
            .launchWith(this, onError = { printError(it) })
    }

    fun printError(error: Throwable) {
        Log.e("TAG", error.localizedMessage)
        shouldShowApiErrorAlert.value = error
    }
}