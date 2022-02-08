package com.orders.ResturantOrder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orders.ResturantOrder.network.RetroRepository
import com.meetSuccess.FoodResturant.Util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.catch

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val mainRepository: RetroRepository)
    : ViewModel(){


    private val categoriesheader: MutableStateFlow<ApiState>
            = MutableStateFlow(ApiState.Empty)
    private val categoriesL: MutableStateFlow<ApiState>
            = MutableStateFlow(ApiState.Empty)


    val _postStateFlowHeader: StateFlow<ApiState> = categoriesheader
    val _postStateFlowLower: StateFlow<ApiState> = categoriesL

    //suspend function always called with courtines

    fun getproductOffersHeader() = viewModelScope.launch {
        categoriesheader.value = ApiState.Loading
        mainRepository.getPost()
                .catch { e->
                    categoriesheader.value=ApiState.Failure(e)
                }.collect { data->
                categoriesheader.value=ApiState.SuccessCategoriesHeader(data)
                }
    }
    fun getLowestCategory() = viewModelScope.launch {
        categoriesL.value = ApiState.Loading
        mainRepository.getLatestMeals()
                .catch { e->
                    categoriesL.value=ApiState.Failure(e)

                }.collect { data->
                categoriesL.value=ApiState.SuccessCategories(data)
                }
    }
}