package com.zain.xdetect.data.remote.model.detailarticle

import com.google.gson.annotations.SerializedName

data class DetailArticleResponse(

	@field:SerializedName("data")
	val data: DataDetailArticle,

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("success")
	val success: String
)
