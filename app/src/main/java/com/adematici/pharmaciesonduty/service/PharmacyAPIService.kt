package com.adematici.pharmaciesonduty.service

import com.adematici.pharmaciesonduty.model.PharmacyModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PharmacyAPIService {
    private val BASE_URL = "https://api.collectapi.com/health/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(PharmacyAPI::class.java)

    fun getData(province: String, district: String): Single<List<PharmacyModel>> {
        return api.getPharmacy(province,district)
    }
}