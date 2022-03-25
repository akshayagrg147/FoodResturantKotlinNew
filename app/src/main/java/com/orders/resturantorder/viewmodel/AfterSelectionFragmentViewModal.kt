package com.orders.resturantorder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.meetSuccess.FoodResturant.Util.ApiState
import com.orders.resturantorder.Base.BaseViewModel
import com.orders.resturantorder.network.RetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class AfterSelectionFragmentViewModal @Inject constructor(private val mainRepository: RetroRepository)
    : BaseViewModel(mainRepository){
    private val postStateFlowAfterSelection: MutableStateFlow<ApiState>
            = MutableStateFlow(ApiState.Empty)
    private val stringMutableLiveData = MutableStateFlow<String>("")

    val _postStateflowAfterSelection: StateFlow<ApiState> = postStateFlowAfterSelection
    val stringvalue: StateFlow<String> = stringMutableLiveData

    //suspend function always called with courtines

    private suspend fun getProductsAfterSelection(productid: Int) =  withContext(Dispatchers.IO){
        postStateFlowAfterSelection.value = ApiState.Loading
        mainRepository.getcateogryAfterSelectionMeals(productid)
            .catch { e->
                postStateFlowAfterSelection.value=ApiState.Failure(e)
            }.collect {

                    data->
                postStateFlowAfterSelection.value=ApiState.SuccessCategories(data)
            }
    }
    suspend  fun  sendData(msg: String) {
        stringMutableLiveData.value = msg
        getProductsAfterSelection(Integer.parseInt(msg))
    }



}