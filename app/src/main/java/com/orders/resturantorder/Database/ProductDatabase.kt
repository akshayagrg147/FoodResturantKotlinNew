package com.meetSuccess.Database

import android.content.Context
import androidx.room.*
import androidx.room.Dao
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.GlobalScope

import kotlinx.coroutines.launch



@Database(entities = [CartItems::class,AddressItems::class],version = 2,exportSchema = true)
abstract class ProductDatabase:RoomDatabase(){
    abstract fun contactDao(): com.meetSuccess.Database.Dao
    companion object{
//juat make to visible to other threads
        @Volatile
        private var INSTANCE: ProductDatabase?=null
        fun getInstance(context: Context):ProductDatabase{
            if (INSTANCE == null) {
                synchronized(ProductDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context, ProductDatabase::class.java, "movie.db").
                    fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE!!        }
    }

}


