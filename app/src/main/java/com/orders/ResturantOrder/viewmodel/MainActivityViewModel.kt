package com.orders.ResturantOrder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meetSuccess.FoodResturant.Model.SearchingPassingData
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
    private  val mutable: MutableLiveData<String> =MutableLiveData("Default value")
    private  val mutableBoolean: MutableLiveData<Boolean> =MutableLiveData(false)


    private val categoriesheader: MutableStateFlow<ApiState>
            = MutableStateFlow(ApiState.Empty)
    private val categoriesL: MutableStateFlow<ApiState>
            = MutableStateFlow(ApiState.Empty)
    private val searchResult: MutableStateFlow<ApiState>
            = MutableStateFlow(ApiState.Empty)


    val _postStateFlowHeader: StateFlow<ApiState> = categoriesheader
    val _postStateFlowLower: StateFlow<ApiState> = categoriesL
    val _searchStateFlow:StateFlow<ApiState> =searchResult

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



    fun getCallingSearchApi(searchingPassingData: SearchingPassingData ) = viewModelScope.launch {
        searchResult.value = ApiState.Loading
        mainRepository.SearchProductPassingString(searchingPassingData)
            .catch { e->
                searchResult.value=ApiState.Failure(e)
            }.collect {
                    data->
                searchResult.value=ApiState.GetResultBasedOnKeywords(data)
            }
    }
    fun passingValue(string:String){
        mutable.value=string

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