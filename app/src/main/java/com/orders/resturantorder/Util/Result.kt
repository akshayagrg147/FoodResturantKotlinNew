package com.orders.resturantorder.Util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response


fun <T> toResultFlow(call: suspend () -> Response<T>): Flow<ApiStatee<T>> = flow {

    emit(ApiStatee.Loading)

    try {
        val response = call()

        if (response.isSuccessful) {
            response.body()?.let {
                emit(ApiStatee.Success(it))
            }
        } else {
            response.errorBody()?.let {error->
                error.close()
                emit(ApiStatee.Failure(error.string()))
            }
        }

    } catch (e: Exception) {
        emit(ApiStatee.Failure(e.message!!))
    }

}.flowOn(Dispatchers.IO)