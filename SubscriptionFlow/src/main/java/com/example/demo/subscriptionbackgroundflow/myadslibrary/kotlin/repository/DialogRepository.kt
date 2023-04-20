package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.repository

import androidx.lifecycle.LiveData
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.db.AppDatabase
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.db.entity.AppEntity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

class DialogRepository(private val db : AppDatabase) {

    fun getData() : LiveData<List<AppEntity>> = runBlocking {
        coroutineScope {
            db.AppDao().getAllWithObserver()
        }
    }

}