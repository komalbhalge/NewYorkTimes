package com.kb.nytimes.di

import com.kb.nytimes.data.remote.ArticlesApi
import com.kb.nytimes.data.repository.ArticlesRepositoryImpl
import com.kb.nytimes.domain.repository.ArticlesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ArticlesModule {
     @Provides
    @Singleton
    fun provideArticlesRepository(api: ArticlesApi): ArticlesRepository =
        ArticlesRepositoryImpl(api)


    @Provides
    @Singleton
    fun provideArticleAPI(retrofit: Retrofit): ArticlesApi =
        retrofit.create(ArticlesApi::class.java)

}