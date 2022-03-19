package com.meetSuccess.FoodResturant.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("data") @Expose
    public var categories: List<Category>


) {


    data class Category(

            @SerializedName("id")
            private val idCategory: Int,
            @SerializedName("name")
            private val name: String,

            @SerializedName("description")
            private val description: String ,

            @SerializedName("image")
            private var image: String,

            @SerializedName("created_at")
            private var created_at: String,
            @SerializedName("updated_at")
            private var updated_at: String




    )
    {
        fun getIdCategory(): Int {
            return idCategory
        }



        fun getname(): String {
            return name
        }



        fun getdescription(): String {
            return description
        }
        fun getPrice(): String {
            return description
        }



        fun getStrimage(): String {
            return image
        }


    }

}


