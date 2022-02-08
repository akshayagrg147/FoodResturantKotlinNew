package com.meetSuccess.FoodResturant.Util

import com.meetSuccess.FoodResturant.Model.*

sealed class ApiState{
    object Loading : ApiState()
    class Failure(val msg:Throwable) : ApiState()
    class SuccessCategories(val data: Categories) : ApiState()
    class SuccessCategoriesHeader(val data: CategoriesHeader) : ApiState()
    class SuccessAfterSelection(val data: cateogryAfterSelectionModal) : ApiState()
    class SuccessMobileRespnse(val data: MobileNumberResponse) : ApiState()
    class CheckExistMobileRespnse(val data: MobileNumberExistCheck) : ApiState()
    class SuccessMeals(val data: Meals) : ApiState()
//    class Success(val data: Meals) : ApiState()
    object Empty : ApiState()
}
