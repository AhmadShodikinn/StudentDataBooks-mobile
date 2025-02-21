package com.project.virtualdatabooks.Data.DataClass

import com.google.gson.annotations.SerializedName

 class ItemJurusan(

	@field:SerializedName("ItemJurusan")
	val itemJurusan: List<ItemJurusanItem>
)

 class ItemJurusanItem(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("id")
	val id: Int
)
