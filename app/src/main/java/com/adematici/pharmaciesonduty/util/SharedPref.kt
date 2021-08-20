package com.adematici.pharmaciesonduty.util

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {

    private var mySharedPref: SharedPreferences = context.getSharedPreferences("sharedPref",Context.MODE_PRIVATE)

    fun setSplashState(state: Boolean){
        val editor = mySharedPref.edit()
        editor.putBoolean("splash", state)
        editor.apply()
    }

    fun getSplashState(): Boolean{
        return mySharedPref.getBoolean("splash",false)
    }

    fun setLocation(province: String, district: String){
        val editor = mySharedPref.edit()
        editor.putString("province",province)
        editor.putString("district",district)
        editor.apply()
    }

    fun getLocationProvince(): String{
        return mySharedPref.getString("province","Ankara")!!
    }

    fun getLocationDistrict(): String{
        return mySharedPref.getString("district","Çankaya")!!
    }

}