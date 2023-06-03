package com.zain.myapplication.data.remote.api

import com.zain.myapplication.data.remote.model.auth.AuthResponse
import com.zain.myapplication.data.remote.model.auth.UserResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("Signup")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phome") phome: String,
        @Field("password") password: String
    ): AuthResponse

    @FormUrlEncoded
    @POST("Signin")
    suspend fun login(
        @Field("name") name: String,
        @Field("password") password: String
    ): AuthResponse

//    @GET("user/{userId}")
//    fun getDataUser(
//        @Path("userId") userId:String
//    ): Call<UserResponse>
//
//    @Multipart
//    @POST("user/{userId}/profile-picture")
//    fun editProfilePicture(
//        @Path("userId") userId:String,
//        @Part file: MultipartBody.Part,
//    ): Call<UserResponse>
//
//    @GET("stories")
//    suspend fun getAllStories(
//        @Header("Authorization") token: String,
//        @Query("page") page: Int? = null,
//        @Query("size") size: Int? = null,
//        @Query("location") location: Int = 0
//    ): AllStoriesResponse
//
//    @GET("stories")
//    suspend fun getAllStoriesWithLocation(
//        @Header("Authorization") token: String,
//        @Query("page") page: Int? = null,
//        @Query("size") size: Int? = 30,
//        @Query("location") location: Int = 1
//    ): AllStoriesResponse
//
//    @GET("stories/{id}")
//    suspend fun getDetailStory(
//        @Header("Authorization") token: String,
//        @Path("id") id: String
//    ): DetailStoryResponse
//
//    @Multipart
//    @POST("stories")
//    suspend fun addStory(
//        @Header("Authorization") token: String,
//        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody,
//        @Part("lat") lat: RequestBody? = null,
//        @Part("lon") lon: RequestBody? = null
//    ): AddStoryResponse
}