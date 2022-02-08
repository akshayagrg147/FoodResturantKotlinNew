package com.meetSuccess.FoodResturant.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName




    data class MobileNumberPassingData(

            @SerializedName("name")
            private val name: String,
            @SerializedName("gender")
            private val gender: String,

            @SerializedName("mobile")
            private val mobile: String? ,

            @SerializedName("dob")
            private var dob: String,




    )
    {
        fun getname(): String {
            return name
        }



        fun getgender(): String {
            return gender
        }



        fun getmobile(): String? {
            return mobile
        }
        fun getdob(): String {
            return dob
        }





    }




