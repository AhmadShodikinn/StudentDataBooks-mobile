package com.project.virtualdatabooks.Data.DataClass

import com.google.gson.annotations.SerializedName

 class ItemAngkatanItem(

	@field:SerializedName("tahun")
	val tahun: Int,

	@field:SerializedName("id")
	val id: Int,

	//ngide dikit
 	@field:SerializedName("jurusan")
	 val jurusan: String
)
