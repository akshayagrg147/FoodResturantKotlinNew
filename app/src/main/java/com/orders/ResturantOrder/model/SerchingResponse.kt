package com.meetSuccess.FoodResturant.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SerchingResponse(

    @SerializedName("message") @Expose
public var message:String,
    @SerializedName("data") @Expose
        public var searchResponse: List<SerchingResponse.SearchResponseModal>


    ) {

    data class SearchResponseModal(
        @SerializedName("id")
        private val idMeal: Int? = null,
        @SerializedName("product_category_id")
        private val product_category_id: String? = null,
        @SerializedName("name")
        private val name: String? = null,
        @SerializedName("description")
        private val description: String? = null,
        @SerializedName("price")
        private val price: String? = null,
        @SerializedName("sale_price")
        private val sale_price: String? = null,
        @SerializedName("dprice")
        private val dprice: String? = null,
        @SerializedName("discount")
        private val discount: String? = null,
        @SerializedName("image")
        private val image: String? = null,
        @SerializedName("image_name")
        private val image_name: String? = null,
        @SerializedName("ingredients")
        private val ingredients: String? = null,
        @SerializedName("menu_status")
        private val menu_status: String? = null,


        ) {


        fun getname(): String? {
            return name
        }
        fun getImageUrl(): String? {
            return image
        }


    }

}

