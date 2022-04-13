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
             val name: String,
            @SerializedName("product_category_id")
            public val product_category_id: String,
            @SerializedName("price")
            private val price: String ,

            @SerializedName("description")
            private val description: String ,

            @SerializedName("image")
             var image: String,

            @SerializedName("created_at")
            private var created_at: String,
            @SerializedName("updated_at")
            private var updated_at: String




    )


}


