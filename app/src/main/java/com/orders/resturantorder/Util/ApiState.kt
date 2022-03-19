package com.meetSuccess.FoodResturant.Util

import com.meetSuccess.FoodResturant.Model.*

sealed class ApiState{
    object Loading : ApiState()
    class Failure(val msg:Throwable) : ApiState()
    class SuccessCategories<T>(val data: T) : ApiState()

    object Empty : ApiState()
}

