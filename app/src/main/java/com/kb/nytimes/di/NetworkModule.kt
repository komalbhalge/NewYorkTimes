package com.kb.nytimes.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kb.nytimes.network.NoConnectivityInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(noConnectivityInterceptor: NoConnectivityInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(noConnectivityInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY))
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    @Singleton
    fun provideBaseUrl() = "https://api.nytimes.com/svc/"//"https://api.nytimes.com/svc/mostpopular/"

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        httpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}