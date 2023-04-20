package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.db.dao

import androidx.room.*
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.db.entity.IdEntity

@Dao
interface IDDao {

    @Insert
    suspend fun insert(vararg entity: IdEntity)

    @Delete
    suspend fun delete(vararg entity: IdEntity)

    @Update
    suspend fun update(entity: IdEntity)

    @Query("DELETE FROM IdEntity")
    suspend fun deleteAll()

//    @Query("UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE name = 'IdEntity'")
//    fun clearPrimaryKey()

    @Query("SELECT * FROM IdEntity WHERE uid = :id")
    suspend fun getAppId(id : Int) : List<IdEntity?>?

    @Query("SELECT * FROM IdEntity")
    suspend fun getAll() : List<IdEntity>

}