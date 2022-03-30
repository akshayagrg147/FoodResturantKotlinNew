package com.orders.resturantorder.network

import com.meetSuccess.FoodResturant.Model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RetroRepository @Inject constructor(private val apiServiceImpl: ApiServiceImpl,
                                      ) {




    suspend fun getPost(): Flow<CategoriesHeader> = flow {
        emit(apiServiceImpl.getPost())
    }.flowOn(Dispatchers.IO)

    suspend fun getLatestMeals():Flow<Categories> = flow {
        emit(apiServiceImpl.getLatestMeals())
    }.flowOn(Dispatchers.IO)

    suspend fun getcateogryAfterSelectionMeals(productid: Int): Flow<cateogryAfterSelectionModal> = flow {
        emit(apiServiceImpl.getAfterSelectCategory(productid))
    }.flowOn(Dispatchers.IO)

    suspend fun SaveUserInformations(mobileNumberData: MobileNumberPassingData): Flow<MobileNumberResponse> = flow {
        emit(apiServiceImpl.SaveUserInformations(mobileNumberData))
    }.flowOn(Dispatchers.IO)
    suspend fun CheckMobileNumberExist(mobileNumberData: String): Flow<MobileNumberExistCheck> = flow {
        emit(apiServiceImpl.CheckMobileNumberExist(mobileNumberData))
    }.flowOn(Dispatchers.IO)
    suspend fun <T:Any> SearchProductPassingString(searchingPassingData: @JvmSuppressWildcards T): Flow<SerchingResponse> = flow {
        emit(apiServiceImpl.SearchProductPassingString(searchingPassingData))
    }.flowOn(Dispatchers.IO)
}