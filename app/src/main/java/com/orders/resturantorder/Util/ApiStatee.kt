package com.orders.resturantorder.Util

sealed class ApiStatee<out T> {

    data class Success<out R>(val data: R) : ApiStatee<R>()
    data class Failure(val msg:String) : ApiStatee<Nothing>()
    object Loading : ApiStatee<Nothing>()

}