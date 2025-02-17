package com.project.virtualdatabooks.Data.Response

import com.google.gson.annotations.SerializedName

data class AdminDashboardResponse(

	@field:SerializedName("count_siswa")
	val countSiswa: Int? = null,

	@field:SerializedName("count_laki")
	val countLaki: Int? = null,

	@field:SerializedName("count_datainputed")
	val countDatainputed: Int? = null,

	@field:SerializedName("count_perempuan")
	val countPerempuan: Int? = null
)
