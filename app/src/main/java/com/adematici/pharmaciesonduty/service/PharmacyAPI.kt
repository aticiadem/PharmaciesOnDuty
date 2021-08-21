package com.adematici.pharmaciesonduty.service

import com.adematici.pharmaciesonduty.model.PharmacyModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PharmacyAPI {
    // https://api.collectapi.com/health/dutyPharmacy?il=Ankara&ilce=Sincan
    @GET("dutyPharmacy?") //?il=Ankara&ilce=Sincan
    suspend fun getPharmacy(
        @Query("il") province: String,
        @Query("ilce") district: String,
        @Header("content-type") type: String = "application/json",
        @Header("authorization") key: String = "" // apikey
    ): Response<PharmacyModel>

}