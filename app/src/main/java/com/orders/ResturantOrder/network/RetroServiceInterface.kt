package com.orders.ResturantOrder.network

import com.meetSuccess.FoodResturant.Model.Categories
import com.meetSuccess.FoodResturant.Model.Meals
import com.meetSuccess.FoodResturant.Model.cateogryAfterSelectionModal
import retrofit2.http.GET
import retrofit2.http.Path

interface RetroServiceInterface {

    @GET("categories")
    suspend fun getCategories():Categories

    @GET("categories.php")
    suspend fun getMeal(): Meals

    @GET("category-product/{productid}")
    suspend fun getMealBasedCategory(@Path("productid")productid: Int): cateogryAfterSelectionModal
}