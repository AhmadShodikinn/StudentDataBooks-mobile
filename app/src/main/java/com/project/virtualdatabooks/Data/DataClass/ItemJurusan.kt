package com.project.virtualdatabooks.Data.DataClass

import com.google.gson.annotations.SerializedName


 class ItemJurusanItem(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("id")
	val id: Int
)
