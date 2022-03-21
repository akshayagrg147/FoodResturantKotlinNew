package com.orders.resturantorder.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.meetSuccess.FoodResturant.Model.SearchingPassingData
import com.orders.resturantorder.network.RetroRepository
import com.meetSuccess.FoodResturant.Util.ApiState
import com.orders.resturantorder.Base.AppUtils
import com.orders.resturantorder.Base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.catch

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class Mainactivityviewmodel @Inject constructor(private val mainRepository: RetroRepository)
    : BaseViewModel(mainRepository){
    private  val mutable: MutableLiveData<String> =MutableLiveData("Default value")
    private  val itemclicked: MutableLiveData<Boolean> =MutableLiveData(false)
    private  val mutableBoolean: MutableLiveData<Boolean> =MutableLiveData(false)


    private val searchResult: MutableStateFlow<ApiState>
            = MutableStateFlow(ApiState.Empty)





    fun tapOnActivity(view: View) = AppUtils.hideKeyboard(view)

    fun getCallingSearchApi(searchingPassingData: SearchingPassingData ) = viewModelScope.launch {
        searchResult.value = ApiState.Loading
        mainRepository.SearchProductPassingString(searchingPassingData)
            .catch { e->
                searchResult.value=ApiState.Failure(e)
            }.collect {
                    data->
                searchResult.value=ApiState.SuccessCategories(data)
            }
    }
    fun passingValue(string:String){
        mutable.value=string

    }
    fun itemclicked(vslue:Boolean){
        itemclicked.value=vslue

    }
    fun getItemClicked(): LiveData<Boolean> {
        return itemclicked

    }
    fun passingSearchClose(boolean: Boolean)
    {
        mutableBoolean.value=boolean
    }
    fun getCloseButtonStataus(): LiveData<Boolean> {
        return mutableBoolean

    }

    fun getSelectedItem(): LiveData<String> {
        return mutable
    }



}