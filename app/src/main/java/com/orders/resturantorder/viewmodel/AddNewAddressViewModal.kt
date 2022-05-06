package com.orders.resturantorder.viewmodel

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.meetSuccess.FoodResturant.Model.SearchingPassingData
import com.meetSuccess.FoodResturant.Util.ApiState
import com.orders.resturantorder.Base.BaseViewModel
import com.orders.resturantorder.R
import com.orders.resturantorder.network.RetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class AddNewAddressViewModal @Inject constructor(private val mainRepository: RetroRepository)
    : BaseViewModel(mainRepository){
    var userName = MutableLiveData<String>()
    fun validatingAddress(view: View) = if (userName.value == null || TextUtils.isEmpty(userName.value!!)) {

    }
    else
    {

    }


}