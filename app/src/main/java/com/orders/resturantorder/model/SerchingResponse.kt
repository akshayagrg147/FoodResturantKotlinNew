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
        @SerializedName("is_category")
        private val is_category: String? = null,


        ) {


        fun getname(): String? {
            return name
        }
        fun getImage(): String? {
            return image_name
        }
        fun getis_category(): String? {
            return is_category
        }
        fun getProductCategory(): String? {
            return product_category_id
        }

        fun getIdCategory(): Int? {
            return idMeal

        }
        fun getidMeal(): Int? {
            return idMeal
        }



        fun getstrproduct_category_id(): String ?{
            return product_category_id
        }



        fun getstrname(): String? {
            return name
        }

        fun getdescription(): String? {
            return description
        }



        fun getstrprice(): String? {
            return price
        }



        fun getsale_price(): String? {
            return sale_price
        }
        fun getdprice(): String? {
            return dprice
        }



        fun getstrdiscount(): String? {
            return discount
        }



        fun getstrimage(): String?{
            return image
        }

    }

}

