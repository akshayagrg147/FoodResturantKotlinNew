package com.orders.resturantorder.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orders.resturantorder.network.RetroRepository
import com.orders.resturantorder.Util.AppUtils
import com.orders.resturantorder.Base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class Mainactivityviewmodel @Inject constructor(private val mainRepository: RetroRepository)
    : BaseViewModel(mainRepository){

    private  val itemclicked: MutableLiveData<Boolean> =MutableLiveData(false)
    private  val mutableBoolean: MutableLiveData<Boolean> =MutableLiveData(false)

    fun tapOnActivity(view: View) = AppUtils.hideKeyboard(view)

    fun getItemClicked(): LiveData<Boolean> {
        return itemclicked

    }
    fun passingSearchClose(boolean: Boolean)
    {
        mutableBoolean.value=boolean
    }



}