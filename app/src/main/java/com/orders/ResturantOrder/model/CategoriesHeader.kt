package com.meetSuccess.FoodResturant.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CategoriesHeader(
    @SerializedName("data") @Expose
    public var categories: List<Category>


) {


    data class Category(

            @SerializedName("id")
            private val idCategory: Int,
            @SerializedName("name")
            private val name: String,
            @SerializedName("product_category_id")
            private val product_category_id: String,
            @SerializedName("price")
            private val price: String ,

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
        fun getIdCategory(): String {
            return product_category_id
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


