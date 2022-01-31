package com.orders.ResturantOrder.network

import com.meetSuccess.FoodResturant.Model.Categories
import com.meetSuccess.FoodResturant.Model.Meals
import retrofit2.http.GET

interface RetroServiceInterface {

    @GET("categories.php")
    suspend fun getCategories():Categories

    @GET("categories.php")
    suspend fun getMeal(): Meals
}