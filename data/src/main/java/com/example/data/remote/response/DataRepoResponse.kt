package com.example.data.remote.response

import com.example.data.model.CarEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataRepoResponse(
    @SerializedName("status") @Expose val status: Int = 0,
    @SerializedName("data") @Expose  val data: List<CarEntity>? = null
)