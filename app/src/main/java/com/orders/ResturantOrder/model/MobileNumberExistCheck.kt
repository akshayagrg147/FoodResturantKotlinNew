package com.meetSuccess.FoodResturant.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MobileNumberExistCheck(

        @SerializedName("status") @Expose
public var status:Int,
        @SerializedName("success") @Expose
public var success:Boolean,
@SerializedName("message") @Expose
public var message:String,
    @SerializedName("existance") @Expose
public var existance:Boolean


) {
    fun getstatus(): Int {
        return status
    }
    fun getsuccess(): Boolean {
        return success
    }
    fun getnmessage(): String {
        return message
    }
    fun getexistance(): Boolean {
        return existance
    }






}

