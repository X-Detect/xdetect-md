package com.zain.xdetect.data.remote.model.detection

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetectionResponse(

    @field:SerializedName("created")
    val created: String,

    @field:SerializedName("additionalInfo")
    val additionalInfo: AdditionalInfo,

    @field:SerializedName("recommendation")
    val recommendation: Recommendation,

    @field:SerializedName("predictions")
    val predictions: Predictions,

    @field:SerializedName("maxLabel")
    val maxLabel: MaxLabel
) : Parcelable

@Parcelize
data class Predictions(

    @field:SerializedName("Mass")
    val mass: String,

    @field:SerializedName("Pneumonia")
    val pneumonia: String,

    @field:SerializedName("Tuberculosis")
    val tuberculosis: String,

    @field:SerializedName("Normal")
    val normal: String,

    @field:SerializedName("Nodule")
    val nodule: String
) : Parcelable

@Parcelize
data class MaxLabel(

    @field:SerializedName("percentage")
    val percentage: String,

    @field:SerializedName("label")
    val label: String
) : Parcelable

@Parcelize
data class AdditionalInfo(

    @field:SerializedName("symptoms")
    val symptoms: List<String>,

    @field:SerializedName("nextSteps")
    val nextSteps: List<String>,

    @field:SerializedName("description")
    val description: String
) : Parcelable

@Parcelize
data class Recommendation(

    @field:SerializedName("action")
    val action: String,

    @field:SerializedName("message")
    val message: String
) : Parcelable