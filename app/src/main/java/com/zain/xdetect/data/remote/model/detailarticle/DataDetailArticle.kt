package com.zain.xdetect.data.remote.model.detailarticle

import com.google.gson.annotations.SerializedName

data class DataDetailArticle(

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("createdBy")
    val createdBy: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("imageURL")
    val imageURL: String,

    @field:SerializedName("sourceURL")
    val sourceURL: String,

//    @field:SerializedName("createdAt")
//    val createdAt: String,

    @field:SerializedName("content")
    val content: String
)
