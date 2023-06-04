package com.zain.xdetect.data.remote.api

import com.zain.xdetect.data.remote.model.article.ArticleResponse
import com.zain.xdetect.data.remote.model.detailarticle.DetailArticleResponse
import retrofit2.Call
import retrofit2.http.*

interface ArticleApiService {

    @GET("article")
    fun getAllArticle():Call<ArticleResponse>

    @GET("article/{id}")
    fun getDetailArticle(
        @Path("id") id:String
    ):Call<DetailArticleResponse>
//
//    @Headers("Authorization: token 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")
//    @GET("users")
//    fun getListUser(
//        @Query("since") since: Int,
//        @Query("per_page") perPage: Int
//    ): Call<List<UserItem>>
//
//    @Headers("Authorization: token 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")
//    @GET("search/users")
//    fun getSearchUser(
//        @Query("q") username: String,
//        @Query("page") page: Int,
//        @Query("per_page") perPage: Int
//    ): Call<SearchUsersResponse>
//
//    @Headers("Authorization: token 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")
//    @GET("users/{username}")
//    fun getDetailUser(
//        @Path("username") username: String
//    ): Call<DetailUser>
//
//    @Headers("Authorization: token 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")
//    @GET("users/{username}/following")
//    fun getListFollowingUser(
//        @Path("username") username: String,
//        @Query("page") page: Int,
//        @Query("per_page") perPage: Int
//    ): Call<List<UserItem>>
//
//    @Headers("Authorization: token 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")
//    @GET("users/{username}/followers")
//    fun getListFollowersUser(
//        @Path("username") username: String,
//        @Query("page") page: Int,
//        @Query("per_page") perPage: Int
//    ): Call<List<UserItem>>
}