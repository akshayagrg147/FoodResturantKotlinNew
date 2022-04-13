package com.meetSuccess.FoodResturant.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName




data class MobileNumberPassingData(

            @SerializedName("name")
            public val name: String,
            @SerializedName("gender")
            public val gender: String,

            @SerializedName("mobile")
            public val mobile: String? ,

            @SerializedName("dob")
            public var dob: String,




    )





