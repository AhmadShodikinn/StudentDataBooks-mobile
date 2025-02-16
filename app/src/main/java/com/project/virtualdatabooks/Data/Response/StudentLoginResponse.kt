package com.project.virtualdatabooks.Data.Response

import com.google.gson.annotations.SerializedName

data class StudentLoginResponse(

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("isMatch")
	val isMatch: Boolean? = null,

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("message")
	val message: String? = "Data tidak ditemukan atau tidak cocok."
)
