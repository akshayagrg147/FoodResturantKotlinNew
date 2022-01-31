package com.meetSuccess.FoodResturant.Util

import com.meetSuccess.FoodResturant.Model.Categories
import com.meetSuccess.FoodResturant.Model.Meals

sealed class ApiState{
    object Loading : ApiState()
    class Failure(val msg:Throwable) : ApiState()
    class SuccessCategories(val data: Categories) : ApiState()
    class SuccessMeals(val data: Meals) : ApiState()
//    class Success(val data: Meals) : ApiState()
    object Empty : ApiState()
}
