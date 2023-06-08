package com.zain.xdetect.data.remote.model.auth

import com.google.gson.annotations.SerializedName

data class DataAuth(

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("email")
	val email: String,
//
//	@field:SerializedName("userId")
//	val userId: Int,
//
//	@field:SerializedName("name")
//	val name: String,
//
//	@field:SerializedName("phone")
//	val phone: String,
//
//	@field:SerializedName("email")
//	val email: String,
//
//	@field:SerializedName("image_url")
//	val image_url: String,
//
//	@field:SerializedName("accessToken")
//	val accessToken: String


)
