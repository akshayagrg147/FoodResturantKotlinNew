package com.orders.ResturantOrder.SharedPreference

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class sharedpreferenceCommon @Inject constructor(mContext: Context){
    private var mPrefs: SharedPreferences = mContext.getSharedPreferences(AppConstant.PREFERENCE_NAME, Context.MODE_PRIVATE)





    fun setUserData(userData: String) : Any { return mPrefs.edit().putString(AppConstant.USER_INFO, userData).apply() }
    fun getUserData(): String { return mPrefs.getString(AppConstant.USER_INFO, "").toString() }




}