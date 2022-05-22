package com.orders.resturantorder.network

import com.meetSuccess.FoodResturant.Model.*
import com.orders.resturantorder.Util.toResultFlow
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: RetroServiceInterface) {

    suspend fun getPost():CategoriesHeader = apiService.getproductOffers()
    suspend fun getLatestMeals():Categories = apiService.getCategories()
    suspend fun getAfterSelectCategory(productid: Int): cateogryAfterSelectionModal = apiService.getMealBasedCategory(productid)
    suspend fun SaveUserInformations(mobileNumberData: MobileNumberPassingData): MobileNumberResponse = apiService.SaveUserInformation(mobileNumberData)
    suspend fun CheckMobileNumberExist(mobileNumberData: String): MobileNumberExistCheck = apiService.getmobileVerify(mobileNumberData)
    suspend fun <T:Any> SearchProductPassingString(searchingPassingData: @JvmSuppressWildcards T): SerchingResponse = apiService.getSearchProductPassingString(searchingPassingData)
    fun RozorPayResponse(
        request: MobileNumberPassingData
    ) = toResultFlow {
        apiService.ApiInplement(request)
    }


//    lifecycleScope.launchWhenStarted {
//        viewmodel.getSearchLibrary("Gurugram", "28.595605", "77.043038", searchkeyword)
//            .collect {
//
//                when (it) {
//                    is ApiState.Success -> {
//                        searchItemsCall(it.data)
//                    }
//                    is ApiState.Failure -> {
//                        context?.showMsg("Something Went Wrong!! ${it.msg}")
//
//                    }
//                    is ApiState.Loading -> {
//
//
//                    }
//                }
//            }
//    }

}