package com.orders.ResturantOrder.network

import com.meetSuccess.FoodResturant.Model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RetroRepository @Inject constructor(private val apiServiceImpl: ApiServiceImpl,
                                      ) {




    fun getPost(): Flow<CategoriesHeader> = flow {
        emit(apiServiceImpl.getPost())
    }.flowOn(Dispatchers.IO)

    fun getLatestMeals():Flow<Categories> = flow {
        emit(apiServiceImpl.getLatestMeals())
    }.flowOn(Dispatchers.IO)

    fun getcateogryAfterSelectionMeals(productid: Int): Flow<cateogryAfterSelectionModal> = flow {
        emit(apiServiceImpl.getAfterSelectCategory(productid))
    }.flowOn(Dispatchers.IO)

    fun SaveUserInformations(mobileNumberData: MobileNumberPassingData): Flow<MobileNumberResponse> = flow {
        emit(apiServiceImpl.SaveUserInformations(mobileNumberData))
    }.flowOn(Dispatchers.IO)
    fun CheckMobileNumberExist(mobileNumberData: String): Flow<MobileNumberExistCheck> = flow {
        emit(apiServiceImpl.CheckMobileNumberExist(mobileNumberData))
    }.flowOn(Dispatchers.IO)
}