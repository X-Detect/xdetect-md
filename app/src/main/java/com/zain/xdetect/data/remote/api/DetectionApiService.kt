package com.zain.xdetect.data.remote.api

import com.zain.xdetect.data.remote.model.detection.DetectionResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface DetectionApiService {

    @Multipart
    @POST("predict")
    suspend fun postDetectionDisease(
//        @Path("uid") uid: String,
        @Part image: MultipartBody.Part,
    ): DetectionResponse

//    @GET("history/{uid}")
//    suspend fun getAllHistory(
//        @Path("uid") uid: String
//    ): HistoryDetectionResponse

}