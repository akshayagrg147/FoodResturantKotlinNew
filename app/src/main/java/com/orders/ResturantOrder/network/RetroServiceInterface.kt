package com.orders.ResturantOrder.network

import com.meetSuccess.FoodResturant.Model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetroServiceInterface {

    @GET("productOffers")
    suspend fun getproductOffers(): CategoriesHeader

    @GET("categories")
    suspend fun getCategories(): Categories

    @GET("category-product/{productid}")
    suspend fun getMealBasedCategory(@Path("productid")productid: Int): cateogryAfterSelectionModal

    @POST("registerUser")
    suspend fun SaveUserInformation(@Body mobileNumberData:MobileNumberPassingData): MobileNumberResponse
    @GET("mobileVerify/{mobile}")
    suspend fun getmobileVerify(@Path("mobile")mobile: String): MobileNumberExistCheck
}