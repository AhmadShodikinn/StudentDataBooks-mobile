package com.project.virtualdatabooks.Data.DataClass

import com.google.gson.annotations.SerializedName

data class ItemSearch(

	@field:SerializedName("ItemSearch")
	val itemSearch: List<ItemSearchItem?>? = null
)

data class ItemSearchItem(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("nisn")
	val nisn: String? = null,

	@field:SerializedName("angkatan")
	val angkatan: Int? = null,

	@field:SerializedName("jurusan")
	val jurusan: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
