package com.project.virtualdatabooks.Data.Response

import com.google.gson.annotations.SerializedName

data class AdminOTPResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
