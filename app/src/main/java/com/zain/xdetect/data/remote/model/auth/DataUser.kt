package com.zain.xdetect.data.remote.model.auth

import com.google.gson.annotations.SerializedName

data class DataUser(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("phone")
    val phone:String?=null,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("image_url")
    val image_url: String,

    @field:SerializedName("access_token")
    val access_token: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)

