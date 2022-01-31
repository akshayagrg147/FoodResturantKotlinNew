package com.orders.ResturantOrder.network

import com.meetSuccess.FoodResturant.Model.Categories
import com.meetSuccess.FoodResturant.Model.Meals
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: RetroServiceInterface) {

    suspend fun getPost():Categories = apiService.getCategories()
    suspend fun getLatestMeals():Meals = apiService.getMeal()
}