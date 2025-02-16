package com.project.virtualdatabooks.Data.Response

import com.google.gson.annotations.SerializedName

data class AdminLoginResponse(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
