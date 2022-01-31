package com.meetSuccess.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "CartItems")
data class CartItems(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "ProductIdNumber")
    val ProductIdNumber: String,
    @ColumnInfo(name = "strCategoryThumb")
    val strCategoryThumb: String,
    @ColumnInfo(name = "totalCount")
    val totalCount: Int,
    @ColumnInfo(name = "productPrice")
    var strProductPrice: Int,

    @ColumnInfo(name = "strCategoryDescription")
    var strCategoryDescription: String,


) {
    constructor(
        ProductIdNumber: String,
        strCategoryThumb: String,
        totalCount: Int,
        price:Int,
        strCategoryDescription: String,

    ) : this(0, ProductIdNumber, strCategoryThumb, totalCount, price,strCategoryDescription)
}


