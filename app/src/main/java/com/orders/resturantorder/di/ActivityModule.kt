package com.orders.resturantorder.di


import com.orders.resturantorder.viewmodel.AddNewAddressViewModal
import com.orders.resturantorder.viewmodel.CartItemsViewModal
import com.orders.resturantorder.viewmodel.ProceedToAddressViewModal
import com.orders.resturantorder.viewmodel.SplashScreenViewModal
import com.orders.resturantorder.network.RetroRepository
import com.orders.resturantorder.viewmodel.Mainactivityviewmodel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

//activty comonent start with your on create and end with ondestroy
@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Singleton
    @Provides
    fun AddNewAddress(dataManager: RetroRepository): AddNewAddressViewModal {
        return AddNewAddressViewModal(dataManager)
    }

    @Singleton
    @Provides
    fun CartItems(dataManager: RetroRepository): CartItemsViewModal {
        return CartItemsViewModal(dataManager)
    }

    @Singleton
    @Provides
    fun ProceedToAddress(dataManager: RetroRepository): ProceedToAddressViewModal {
        return ProceedToAddressViewModal(dataManager)
    }

    @Singleton
    @Provides
    fun SplashScreen(dataManager: RetroRepository): SplashScreenViewModal {
        return SplashScreenViewModal(dataManager)
    }
    @Singleton
    @Provides
    fun MainActivity(dataManager: RetroRepository): Mainactivityviewmodel {
        return Mainactivityviewmodel(dataManager)
    }


}