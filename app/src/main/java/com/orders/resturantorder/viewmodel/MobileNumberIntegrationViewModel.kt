package com.orders.resturantorder.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meetSuccess.FoodResturant.Model.MobileNumberPassingData
import com.orders.resturantorder.network.RetroRepository
import com.meetSuccess.FoodResturant.Util.ApiState
import com.orders.resturantorder.Base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.catch

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MobileNumberIntegrationViewModel @Inject constructor(private val mainRepository: RetroRepository)
    : BaseViewModel(mainRepository){


    private val mobilenumberCheck: MutableStateFlow<ApiState>
            = MutableStateFlow(ApiState.Empty)
    private val savedinformation: MutableStateFlow<ApiState>
            = MutableStateFlow(ApiState.Empty)




    val mobilenumberCheckFlowstate: StateFlow<ApiState> = mobilenumberCheck
    val savedinformationCheckFlowstate: StateFlow<ApiState> = savedinformation

    //suspend function always called with courtines



    fun CheckMobileNumberExist( mobileNumberData: String ) = viewModelScope.launch {
        mobilenumberCheck.value = ApiState.Loading
        mainRepository.CheckMobileNumberExist(mobileNumberData)
                .catch { e->
                    mobilenumberCheck.value=ApiState.Failure(e)
                }.collect {

                    data->
                    mobilenumberCheck.value=ApiState.CheckExistMobileRespnse(data)
                }
    }

    fun SaveUserInformations( mobileNumberData: MobileNumberPassingData ) = viewModelScope.launch {
        Log.d("callingtest", "call1")
        savedinformation.value = ApiState.Loading
        mainRepository.SaveUserInformations(mobileNumberData)
                .catch {  Log.d("callingtest", "call3")
                }.collect {

                    data->
                    savedinformation.value=ApiState.SuccessMobileRespnse(data)
                }
    }


}