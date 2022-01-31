package com.meetSuccess.FoodResturant.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Meals(
    @SerializedName("meals") @Expose
    public var meals: List<Meal>


) {


    data class Meal(
        @SerializedName("idMeal")
        private val idMeal: String? = null,
        @SerializedName("strMeal")
        private val strMeal: String,

        @SerializedName("strDescription")
        private val strDescription: String? = null,


    )
    {
        fun getIdMeal(): String? {
            return idMeal
        }



        fun getstrMeal(): String {
            return strMeal
        }



        fun getstrDescription(): String? {
            return strDescription
        }



    }

}

