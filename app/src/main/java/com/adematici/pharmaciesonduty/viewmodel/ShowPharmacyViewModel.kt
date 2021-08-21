package com.adematici.pharmaciesonduty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adematici.pharmaciesonduty.model.Result
import com.adematici.pharmaciesonduty.service.PharmacyAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowPharmacyViewModel: ViewModel() {

    private val servicePharmacy = PharmacyAPIService()

    val variables = MutableLiveData<List<Result>>()
    val progressBarState = MutableLiveData<Boolean>()
    val errorMessageState = MutableLiveData<Boolean>()

    fun getPharmacyData(province: String, district: String){
        getData(province,district)
    }

    private fun getData(province: String, district: String) {
        progressBarState.value = true
        viewModelScope.launch(Dispatchers.IO){
            val response = servicePharmacy.getData(province,district)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {
                        variables.value = it.result
                        progressBarState.value = false
                        errorMessageState.value = false
                    }
                } else {
                    progressBarState.value = false
                    errorMessageState.value = true
                }
            }
        }
    }

}