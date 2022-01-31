package com.orders.ResturantOrder.network

import com.meetSuccess.FoodResturant.Model.Categories
import com.meetSuccess.FoodResturant.Model.Meals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RetroRepository @Inject constructor(private val apiServiceImpl: ApiServiceImpl,
                                      ) {




    fun getPost(): Flow<Categories> = flow {
        emit(apiServiceImpl.getPost())
    }.flowOn(Dispatchers.IO)

    fun getLatestMeals():Flow<Meals> = flow {
        emit(apiServiceImpl.getLatestMeals())
    }.flowOn(Dispatchers.IO)
}