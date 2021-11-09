package com.kb.nytimes.data.remote

import com.kb.nytimes.data.model.MostPopularArticlesResponse
import com.kb.nytimes.data.model.SearchedArticlesResponse
import com.kb.nytimes.data.model.SectionsResponse
import com.kb.nytimes.util.Constants.API_KEY
import com.kb.nytimes.util.Constants.API_KEY_VALUE
import com.kb.nytimes.util.Constants.LIMIT
import com.kb.nytimes.util.Constants.OFFSET
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticlesApi {
    //https://api.nytimes.com/svc/mostpopular/v2/emailed/7.json?api-key=
    @GET("mostpopular/v2/emailed/{period}.json")
    suspend fun mostEmailedArticles(
        @Path("period") period: Int,
        @Query(API_KEY) timeSpan: String? = API_KEY_VALUE
    ): MostPopularArticlesResponse

//    @GET("v2/mostviewed/all-sections/7.json")
//    Single<ArticalesRemote> getArticles(@Query("api-key") String apiKey);

    //https://api.nytimes.com/svc/search/v2/articlesearch.json?q=t&facet=true&facet_fields=news_desk&fq=World&api-key=
    @GET("search/v2/articlesearch.json") //For the same of simplicity, added static fields herw
    suspend fun searchArticles(
        @Query("page") page: Int? = 2,
        @Query("q") searchResult: String,
        @Query("facet") facet: Boolean? = false,
        @Query("facet_fields") facet_field: String? = "news_desk",
        @Query(API_KEY) timeSpan: String? = API_KEY_VALUE
    ): SearchedArticlesResponse

    //https://api.nytimes.com/svc/mostpopular/v2/shared/1/facebook.json?api-key=
    @GET("mostpopular/v2/shared/{period}/facebook.json")
    suspend fun mostSharedArticles(
        @Path("period") period: Int,
        @Query(API_KEY) timeSpan: String? = API_KEY_VALUE
    ): MostPopularArticlesResponse

    //https://api.nytimes.com/svc/mostpopular/v2/viewed/1.json?api-key=
    @GET("mostpopular/v2/viewed/{period}.json")
    suspend fun mostViewedArticles(
        @Path("period") period: Int,
        @Query(API_KEY) timeSpan: String? = API_KEY_VALUE
    ): MostPopularArticlesResponse

    /*//https://api.nytimes.com/svc/mostpopular/v2/viewed/1.json?api-key=
    @GET("v2/mostviewed/all-sections/{noOfDays}.json")
    suspend fun mostViewedArticles(
        @Path("noOfDays") noOfDays: Int,
        @Query(API_KEY) timeSpan: String? = API_KEY_VALUE
    ): MostPopularArticlesResponse*/

    //TODO-Extra part
    //https://api.nytimes.com/svc/news/v3/content/{source}/{section}.json
    @GET("news/v3/content/all/{section}.json")
    suspend fun getArticlesBySection(
        @Path("section") section: Int,
        @Query(API_KEY) timeSpan: String? = API_KEY_VALUE,
        @Query(LIMIT) limit: Int = 500,
        @Query(OFFSET) offset: Int = 20,
    ): SectionsResponse

    //https://api.nytimes.com/svc/news/v3/content/section-list.json
    @GET("news/v3/content/section-list.json")
    suspend fun getAllSections(
        @Query(API_KEY) timeSpan: String? = API_KEY_VALUE
    ): SectionsResponse
}