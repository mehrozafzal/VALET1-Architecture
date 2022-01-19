package com.mehroz.valet1_task.data.remote.response

import com.google.gson.annotations.SerializedName

data class DevicesResponse(

	@field:SerializedName("DevicesResponse")
	val devicesResponse: MutableList<DevicesResponseItem>? = null
)

data class DevicesResponseItem(

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("Type")
	val type: String? = null,

	@field:SerializedName("Description")
	val description: String? = null,

	@field:SerializedName("Price")
	val price: Int? = null,

	@field:SerializedName("Currency")
	val currency: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("isFavorite")
	val isFavorite: Boolean? = null
)
