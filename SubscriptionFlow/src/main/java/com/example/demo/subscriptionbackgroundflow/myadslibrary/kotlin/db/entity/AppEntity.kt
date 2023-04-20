package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppEntity(

        @ColumnInfo(name = "position")
        val position: Int,

        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "app_link")
        val app_link: String,

        @ColumnInfo(name = "image")
        val image: String,

        @ColumnInfo(name = "is_trending")
        val is_trending: Boolean,

        @PrimaryKey(autoGenerate = true)
        val uid: Int = 0

) {

}