package com.zain.myapplication.data.remote.model.auth

import com.google.gson.annotations.SerializedName

data class AuthResponse(

    @field:SerializedName("data")
    val data: DataAuth,

    @field:SerializedName("msg")
    val msg: String,

    @field:SerializedName("success")
    val success: Boolean
)