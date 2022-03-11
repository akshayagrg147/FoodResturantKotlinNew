package com.meetSuccess.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCartItem(contact: CartItems)
    @Query("DELETE  FROM CartItems WHERE ProductIdNumber = :id" )
    fun deleteCartItem(id:String?)
    @Query("UPDATE CartItems SET totalCount=:count WHERE ProductIdNumber = :id")
    fun updateCartItem(count: Int,id:String?)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAddressItem(address: AddressItems)
    @Query("SELECT * FROM AddressItems")
    fun getAllAddress():LiveData<List<AddressItems>>

    @Query("SELECT SUM(productPrice*totalCount) FROM CartItems")
    fun getTotalPrice():LiveData<Int>

  @Query("SELECT SUM(totalCount) FROM CartItems")
     fun getTotalProductItems(): LiveData<Int>
    @Query("SELECT SUM(totalCount) FROM CartItems")
    fun getTotalCartItems():Int
    @Query("SELECT * FROM CartItems")
    fun getContact():LiveData<List<CartItems>>
    @Query("SELECT totalCount FROM CartItems where ProductIdNumber=:ProductIdNumber")
    fun getProductBasedIdCount( ProductIdNumber:String?):Int
    @Query("SELECT * FROM CartItems where ProductIdNumber=:ProductIdNumber")
    fun getProductBasedId( ProductIdNumber:String?):CartItems

    @Query("DELETE  FROM AddressItems WHERE customer_name = :customername and Address1 = :address1 and Address2 = :address2" )
    fun deleteAddressItems(customername:String,address1:String,address2:String)
}