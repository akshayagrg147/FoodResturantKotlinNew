package com.orders.resturantorder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class AfterCategorySelectionViewModel @Inject constructor(private val mainRepository: RetroRepository)
    : BaseViewModel(mainRepository){


    private val postStateFlowAfterSelection: MutableStateFlow<ApiState>
            = MutableStateFlow(ApiState.Empty)

    val _postStateflowAfterSelection: StateFlow<ApiState> = postStateFlowAfterSelection

    //suspend function always called with courtines

    fun getProductsAfterSelection( productid: Int) = viewModelScope.launch {
        postStateFlowAfterSelection.value = ApiState.Loading
        mainRepository.getcateogryAfterSelectionMeals(productid)
                .catch { e->
                    postStateFlowAfterSelection.value=ApiState.Failure(e)
                }.collect {

                    data->
                    postStateFlowAfterSelection.value=ApiState.SuccessCategories(data)
                }
    }

}