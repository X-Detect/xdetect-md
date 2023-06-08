package com.zain.xdetect.data.remote.model.auth

import com.google.gson.annotations.SerializedName

data class AuthResponse(

    @field:SerializedName("data")
    val data: DataAuth? = null,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)
//    @field:SerializedName("msg")
//    val msg: String,
//
//    @field:SerializedName("success")
//    val success: Boolean

