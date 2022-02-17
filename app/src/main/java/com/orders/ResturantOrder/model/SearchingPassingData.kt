package com.meetSuccess.FoodResturant.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName




    data class SearchingPassingData(

            @SerializedName("search")
            private val search: String,
//            @SerializedName("page")
//            private val page: String,






    )
    {




        fun getmobile(): String? {
            return search
        }
        fun getdob(): String {
            return search
        }





    }




