package com.adematici.pharmaciesonduty.model


import com.google.gson.annotations.SerializedName

data class PharmacyModel(
    @SerializedName("result")
    val result: List<Result>,
    @SerializedName("success")
    val success: Boolean
)