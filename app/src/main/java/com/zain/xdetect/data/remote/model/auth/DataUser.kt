package com.zain.xdetect.data.remote.model.auth

import com.google.gson.annotations.SerializedName

data class DataUser(

    @field:SerializedName("phone")
    val phone: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("profilePicture")
    val profilePicture: String? = null,

    @field:SerializedName("history")
    val history: DataHistory? = null
)

