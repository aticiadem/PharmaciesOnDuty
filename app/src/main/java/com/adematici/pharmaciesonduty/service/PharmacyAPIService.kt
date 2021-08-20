package com.adematici.pharmaciesonduty.service

import com.adematici.pharmaciesonduty.model.PharmacyModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PharmacyAPIService {
    private val BASE_URL = "https://api.collectapi.com/health/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(PharmacyAPI::class.java)

    suspend fun getData(province: String, district: String): Response<PharmacyModel> {
        return api.getPharmacy(province.lowercase(),district.lowercase())
    }
}