package com.meetSuccess.FoodResturant.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MobileNumberResponse(

        @SerializedName("message") @Expose
public var message:String,
        @SerializedName("status") @Expose
        public var status:Int,
        @SerializedName("existance") @Expose
        public var existance:String


) {
    fun getnmessage(): String {
        return message
    }






}

