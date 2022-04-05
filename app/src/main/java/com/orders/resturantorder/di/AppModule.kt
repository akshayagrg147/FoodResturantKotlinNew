package com.orders.resturantorder.di

import android.app.Application
import com.meetSuccess.Database.Dao
import com.meetSuccess.Database.ProductDatabase

import com.orders.resturantorder.network.RetroServiceInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


//SingletonComponent remains until start your application and end with your application


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun getAppDatabase(context: Application): ProductDatabase {
        return ProductDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun getAppDao(appDatabase: ProductDatabase): Dao {
        return appDatabase.contactDao()
    }

    val BASE_URL = "https://chuimui.in/public/api/"

    @Provides
    @Singleton
    fun getRetroServiceInstance(retrofit: Retrofit): RetroServiceInterface {
        return retrofit.create(RetroServiceInterface::class.java)
    }

    @Provides
    @Singleton
    fun getRetroInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}