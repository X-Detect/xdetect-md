package com.zain.xdetect.data.remote.api

import com.zain.xdetect.data.remote.model.auth.AuthResponse
import com.zain.xdetect.data.remote.model.auth.UserResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("sign-up")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("phone") phone: String
    ): AuthResponse

    @FormUrlEncoded
    @POST("sign-in")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): AuthResponse

//    @FormUrlEncoded
//    @POST("signin")
//    suspend fun login(
//        @Field("name") name: String,
//        @Field("password") password: String
//    ): AuthResponse

    @GET("user/{uid}")
    suspend fun getDataUser(
        @Path("uid") uid: String
    ): UserResponse

}