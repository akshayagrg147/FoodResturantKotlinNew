package com.meetSuccess.FoodResturant.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SerchingResponse(

    @SerializedName("message") @Expose
    public var message: String,
    @SerializedName("data") @Expose
    public var searchResponse: List<SerchingResponse.SearchResponseModal>
) {

    data class SearchResponseModal(
        @SerializedName("id")
        val idMeal: Int? = null,
        @SerializedName("product_category_id")
        val product_category_id: String? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("description")
        val description: String? = null,
        @SerializedName("price")
        val price: String? = null,
        @SerializedName("sale_price")
        val sale_price: String? = null,
        @SerializedName("dprice")
        val dprice: String? = null,
        @SerializedName("discount")
        private val discount: String? = null,
        @SerializedName("image")
        val image: String? = null,
        @SerializedName("image_name")
        private val image_name: String? = null,
        @SerializedName("ingredients")
        private val ingredients: String? = null,
        @SerializedName("menu_status")
        private val menu_status: String? = null,
        @SerializedName("is_category")
        val is_category: String? = null,
    )

}

