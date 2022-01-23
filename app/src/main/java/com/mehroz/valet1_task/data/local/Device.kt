package com.mehroz.valet1_task.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "devices")
data class Device(

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

    @PrimaryKey
    @field:SerializedName("Id")
    val id: Int,

    @field:SerializedName("isFavorite")
    val isFavorite: Boolean? = null
)
