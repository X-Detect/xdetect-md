package com.zain.xdetect.data.remote.model.auth

import com.google.gson.annotations.SerializedName

data class DataHistory(

    @field:SerializedName("detection_img")
    val detection_img: String,

    @field:SerializedName("predicted_class")
    val predicted_class: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("datetime")
    val datetime: String
)

