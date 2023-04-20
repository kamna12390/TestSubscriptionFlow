package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IdEntity (

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "uid")
    val uid : Int,

    @ColumnInfo(name = "name")
    val name : String,

    @ColumnInfo(name = "category_id")
    val category_id : Int,

    @ColumnInfo(name = "category_name")
    val category_name : String,

    @ColumnInfo(name = "google")
    val google : String?,

    @ColumnInfo(name = "facebook")
    val facebook : String?
)