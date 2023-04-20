package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.db.dao.AppDao
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.db.dao.IDDao
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.db.entity.AppEntity
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.db.entity.IdEntity

@Database(entities = [AppEntity::class, IdEntity::class],version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun AppDao() : AppDao

    abstract fun IdDao() : IDDao

    companion object{

        private const val name = "local_app_database"

        private var db : AppDatabase? = null

        fun getInstance(context : Context) : AppDatabase {
            if(db == null) {
                db = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java, name
                ).build()
            }
            return db!!
        }

    }
}