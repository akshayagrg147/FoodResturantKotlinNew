package com.orders.resturantorder.network

import com.meetSuccess.FoodResturant.Model.*
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: RetroServiceInterface) {

    suspend fun getPost():CategoriesHeader = apiService.getproductOffers()
    suspend fun getLatestMeals():Categories = apiService.getCategories()
    suspend fun getAfterSelectCategory(productid: Int): cateogryAfterSelectionModal = apiService.getMealBasedCategory(productid)
    suspend fun SaveUserInformations(mobileNumberData: MobileNumberPassingData): MobileNumberResponse = apiService.SaveUserInformation(mobileNumberData)
    suspend fun CheckMobileNumberExist(mobileNumberData: String): MobileNumberExistCheck = apiService.getmobileVerify(mobileNumberData)
    suspend fun SearchProductPassingString(searchingPassingData: SearchingPassingData): SerchingResponse = apiService.getSearchProductPassingString(searchingPassingData)

}