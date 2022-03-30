package com.orders.resturantorder.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.meetSuccess.FoodResturant.Model.SearchingPassingData
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
class DashBoardCategoriesViewModal @Inject constructor(private val mainRepository: RetroRepository)
    : BaseViewModel(mainRepository){
    init {
        viewModelScope.launch(Dispatchers.IO){

            getproductOffersHeader()
            getLowestCategory()

            }


        }


    private  val mutable: MutableLiveData<String> = MutableLiveData("Default value")
    private  val itemclicked: MutableLiveData<Boolean> = MutableLiveData(false)
    private  val mutableBoolean: MutableLiveData<Boolean> = MutableLiveData(false)


    private val categoriesheader: MutableStateFlow<ApiState>
            = MutableStateFlow(ApiState.Empty)
    private val categoriesL: MutableStateFlow<ApiState>
            = MutableStateFlow(ApiState.Empty)
    private val searchResult: MutableStateFlow<ApiState>
            = MutableStateFlow(ApiState.Empty)


    val _postStateFlowHeader: StateFlow<ApiState> = categoriesheader
    val _postStateFlowLower: StateFlow<ApiState> = categoriesL
    val _searchStateFlow: StateFlow<ApiState> =searchResult

    //suspend function always called with courtines

    suspend fun getproductOffersHeader() =  withContext(Dispatchers.IO){
        categoriesheader.value = ApiState.Loading
        mainRepository.getPost()
            .catch { e->
                categoriesheader.value= ApiState.Failure(e)
            }.collect { data->
                categoriesheader.value= ApiState.SuccessCategories(data)
            }
    }
    suspend  fun getLowestCategory() = withContext(Dispatchers.IO){
        categoriesL.value = ApiState.Loading
        mainRepository.getLatestMeals()
            .catch { e->
                categoriesL.value= ApiState.Failure(e)

            }.collect { data->
                categoriesL.value= ApiState.SuccessCategories(data)
            }
    }



   suspend fun getCallingSearchApi(searchingPassingData: String) = withContext(Dispatchers.IO){
        searchResult.value = ApiState.Loading
        mainRepository.SearchProductPassingString(searchingPassingData)
            .catch { e->
                searchResult.value= ApiState.Failure(e)
                Log.d("passing","fail"+e.message)
            }.collect {
                    data->
                searchResult.value= ApiState.SuccessCategories(data)
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