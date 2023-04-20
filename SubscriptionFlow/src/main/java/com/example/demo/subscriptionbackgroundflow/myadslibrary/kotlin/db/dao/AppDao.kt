package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.db.entity.AppEntity

@Dao
interface AppDao {

    @Insert
    suspend fun insert(users: AppEntity)

    @Update
    suspend fun update(vararg users: AppEntity)

    @Delete
    suspend fun delete(user: AppEntity)

    @Query("DELETE FROM AppEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM AppEntity")
    fun getAllWithObserver(): LiveData<List<AppEntity>>

//    @Query("UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE name = 'AppEntity'")
//    fun clearPrimaryKey()

    @Query("SELECT * FROM AppEntity")
    suspend fun getAll(): List<AppEntity>

    @Query("SELECT * FROM AppEntity WHERE app_link = :link")
    suspend fun getApp(link: String): AppEntity?
}