package com.meetSuccess.FoodResturant.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class cateogryAfterSelectionModal(
    @SerializedName("data") @Expose
    public var meals: List<cateogryAfterSelectionModal1>,
//        @SerializedName("message") @Expose
//public var message:String


) {
    data class cateogryAfterSelectionModal1(
        @SerializedName("id")
        public val idMeal: Int? = null,
        @SerializedName("product_category_id")
        public val product_category_id: String? = null,
        @SerializedName("name")
        public val name: String? = null,
        @SerializedName("description")
        public val description: String? = null,
        @SerializedName("price")
        public val price: String? = null,
        @SerializedName("sale_price")
        public val sale_price: String? = null,
        @SerializedName("dprice")
        public val dprice: String? = null,
        @SerializedName("discount")
        public val discount: String? = null,
        @SerializedName("image")
        public val image: String? = null,
        @SerializedName("image_name")
        public val image_name: String? = null,
        @SerializedName("ingredients")
        public val ingredients: String? = null,
        @SerializedName("menu_status")
        public val menu_status: String? = null,


        )
}


